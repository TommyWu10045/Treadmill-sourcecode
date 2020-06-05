/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rtx.treadmill.Engmode.wifi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.EditText;

import com.rtx.treadmill.Engmode.EngWifiFrame;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class WifiController implements DialogInterface.OnClickListener{
    static final String TAG = "Jerry=";
    private static boolean DEBUG = false;

    private static Context mContext;

    private static volatile WifiController sInstance;

    private static WifiManager mWifiManager;

    private List<WifiConfiguration> mWifiConfigurations;
    private List<ScanResult> mScanResults = null;

    private static ArrayList<AccessPoint> accesspoints = null;
    private WifiInfo mLastInfo;
    private NetworkInfo mLastNetworkInfo;

    private AccessPoint mSelectedAccessPoint;
    private WifiDialog mDialog;
    private boolean mDlgEdit;

    private int iSelect_id = 0 ;
    public String sinput = "" ;


    /** A restricted multimap for use in constructAccessPoints */
    private static class Multimap<K,V> {
        private final HashMap<K,List<V>> store = new HashMap<K,List<V>>();
        /** retrieve a non-null list of values with key K */
        List<V> getAll(K key) {
            List<V> values = store.get(key);
            return values != null ? values : Collections.<V>emptyList();
        }

        void put(K key, V val) {
            List<V> curVals = store.get(key);
            if (curVals == null) {
                curVals = new ArrayList<V>(3);
                store.put(key, curVals);
            }
            curVals.add(val);
        }
    }

    public static WifiController getInstance(Context context, WifiManager mwifi, ArrayList<AccessPoint> AccessPoints)
    {
        mContext = context;
        if (sInstance == null) {
            synchronized(TAG) {
                if (sInstance == null) {
                    sInstance = new WifiController(context, mwifi, AccessPoints);
                }
            }
        }
        return sInstance;
    }

    public WifiController(Context context, WifiManager mwifi, ArrayList<AccessPoint> AccessPoints)
    {
        mWifiManager = mwifi ;
        accesspoints = AccessPoints ;
    }

    public void wifiscan_sort( ) {
        if(accesspoints == null)
        {
            return;
        }

        accesspoints.clear();

        //wifi setting
        if(mWifiManager.isWifiEnabled()) {
            mWifiConfigurations = mWifiManager.getConfiguredNetworks();
            mLastInfo = mWifiManager.getConnectionInfo();
            mScanResults = mWifiManager.getScanResults();

            AccessPoint caccessPoint = null;
            ArrayList<AccessPoint> accessPoints_temp = new ArrayList<AccessPoint>();
            Multimap<String, AccessPoint> apMap_config = new Multimap<String, AccessPoint>();
            Multimap<String, AccessPoint> apMap_result = new Multimap<String, AccessPoint>();

            if (mWifiConfigurations != null) {
                for (WifiConfiguration config : mWifiConfigurations) {
                    AccessPoint accessPoint = new AccessPoint(mContext, config);
                    if(DEBUG)Log.e(TAG, "======accessPoint=" + accessPoint.ssid);

                    if(apMap_config.getAll(accessPoint.ssid).isEmpty()) {
                        accessPoint.update(mLastInfo, mLastNetworkInfo);
                        apMap_config.put(accessPoint.ssid, accessPoint);

                    }
                }
            }

            if (mScanResults != null) {
                for (ScanResult result : mScanResults) {
                    // Ignore hidden and ad-hoc networks.
                    if(DEBUG)Log.e(TAG, "======result.BSSID=" + result.BSSID);
                    if (result.SSID == null || result.SSID.length() == 0 ||
                            result.capabilities.contains("[IBSS]")) {
                        continue;
                    }

                    AccessPoint accessPoint = new AccessPoint(result);
                    if(!apMap_config.getAll(result.SSID).isEmpty()) {
                        if (result.SSID.compareTo(accessPoint.removeDoubleQuotes(mLastInfo.getSSID())) == 0) {
                            caccessPoint = apMap_config.getAll(result.SSID).get(0);
                            caccessPoint.update(result);
                        } else {
                            AccessPoint accesstemp = apMap_config.getAll(result.SSID).get(0);
                            if (accesstemp != null)
                            {
                                boolean found = false;
                                for (AccessPoint atemp : accessPoints_temp) {
                                    if (atemp.ssid.compareTo(accesstemp.ssid) == 0) {
                                        Log.e(TAG, "===atemp.ssid===" + atemp.ssid + "===accesstemp.ssid===" + accesstemp.ssid);
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    accessPoints_temp.add(accesstemp);
                                    accesstemp.update(result);
                                }
                            }
                        }
                    } else if(apMap_result.getAll(result.SSID).isEmpty()){
                        accessPoint.update(mLastInfo, mLastNetworkInfo);
                        accesspoints.add(accessPoint);
                        apMap_result.put(accessPoint.ssid, accessPoint);
                    }
                }
            }

            sortByLevel(accesspoints);
            sortByLevel(accessPoints_temp);

            for (AccessPoint result : accessPoints_temp) {
                Log.e(TAG, "======result.ssid=" + result.ssid);
                accesspoints.add(0,result);
            }

            if (caccessPoint != null) {
                accesspoints.add(0, caccessPoint);
            }
        }
        return ;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        Log.e(TAG, "===DialogInterface====button=" + button);
        /*
            BUTTON_SUBMIT = BUTTON_POSITIVE = -1;
            BUTTON_CANCEL = BUTTON_NEGATIVE = -2;
            BUTTON_FORGET = BUTTON_NEUTRAL = -3;
         */

        Rtx_Keyboard.closeViewSoftKeybord(mDialog.getCurrentFocus(), mContext);

        if (button == WifiDialog.BUTTON_FORGET && mSelectedAccessPoint != null) {
            forget();
        } else if (button == WifiDialog.BUTTON_SUBMIT) {
            if (mDialog != null) {
                submit(mDialog.getController());
            }
        }
        EngWifiFrame.brescan = true ;

        //sent reflash action
        mContext.sendBroadcast(new Intent(WifiManager.WIFI_STATE_CHANGED_ACTION));
    }

    /* package */ void forget() {
        Log.e(TAG, "===DialogInterface====mSelectedAccessPoint.networkId=" + mSelectedAccessPoint.networkId);
        if (mSelectedAccessPoint.networkId != -1) {
            mWifiManager.disableNetwork(mSelectedAccessPoint.networkId);
            mWifiManager.removeNetwork(mSelectedAccessPoint.networkId);
        }
    }

    /* package */ void submit(WifiConfigController configController) {
        final WifiConfiguration config = configController.getConfig();
        Log.e(TAG, "===DialogInterface====config=" + config);

        if(config == null)
        {
            if (mSelectedAccessPoint != null
                    && mSelectedAccessPoint.networkId != -1) {
                mWifiManager.enableNetwork(mSelectedAccessPoint.networkId, true);
            }
        } else if (config.networkId != -1) {
            if (mSelectedAccessPoint != null) {
                mWifiManager.addNetwork(config);
            }
        } else {
            if (configController.isEdit()) {
                mWifiManager.addNetwork(config);
            } else {
                int anetmsg = mWifiManager.addNetwork(config);
                if(anetmsg >= 0)
                {
                    mWifiManager.enableNetwork(anetmsg, true);
                }
            }
        }
    }

    private void showDialog(AccessPoint accessPoint, boolean edit) {
        if (mDialog != null) {
            mDialog = null;
        }
        // Save the access point and edit mode
        mSelectedAccessPoint = accessPoint;
        mDlgEdit = edit;

        mDialog = new WifiDialog(mContext, this, mSelectedAccessPoint, mDlgEdit);
        mDialog.show();
    }

    public void selectid(AccessPoint accesspoint)
    {
        if(accesspoint != null)
        {
            mSelectedAccessPoint = accesspoint;
            showDialog(mSelectedAccessPoint, false);

            /*if(accesspoint.security == 0)
            {
                wifi_connect(accesspoint, null);
            }
            else if(accesspoint.security == 1 || accesspoint.security == 2)
            {
                NeedPassword(accesspoint);
            }
            */
        }
    }

    private void wifi_connect(AccessPoint result, String spass){
        WifiConfiguration config = new WifiConfiguration();

        if(DEBUG)Log.e(TAG, "======result.ssid===" + result.ssid + "=====spass====" + spass);
        if(result.security == 0 || spass == null)
        {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        else if(result.security == 1)
        {
            config.SSID = AccessPoint.convertToQuotedString(result.ssid);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            if (spass.length() != 0) {
                int length = spass.length();
                // WEP-40, WEP-104, and 256-bit WEP (WEP-232?)
                if ((length == 10 || length == 26 || length == 58) &&
                        spass.matches("[0-9A-Fa-f]*")) {
                    config.wepKeys[0] = spass;
                } else {
                    config.wepKeys[0] = '"' + spass + '"';
                }
            }
        }
        else if ( result.security == 2)
        {
            config.SSID = AccessPoint.convertToQuotedString(result.ssid);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            if (spass.matches("[0-9A-Fa-f]{64}")) {
                config.preSharedKey = spass;
            } else {
                config.preSharedKey = '"' + spass + '"';
            }
        }
        else
        {
            EngWifiFrame.brescan = true ;
            return;
        }

        int netid = mWifiManager.addNetwork(config);
        mWifiManager.enableNetwork(netid, true);
        EngWifiFrame.brescan = true ;
        return;
    }


    private void wifi_connect_password(AccessPoint accesspoint, String spass){
        wifi_connect(accesspoint, spass);
    }

    private void NeedPassword(final AccessPoint apoint)
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(apoint.ssid);
        alert.setMessage("Enter Network Password");
        // Set an EditText view to get user input
        final EditText input = new EditText(mContext);
        alert.setView(input);
        alert.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                sinput = input.getText().toString().trim();
                wifi_connect_password(apoint, sinput);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EngWifiFrame.brescan = true ;
                // Canceled.
            }
        });
        alert.show();
        return ;
    }

    private void sortByLevel(ArrayList<AccessPoint> list) {

        Collections.sort(list, new Comparator<AccessPoint>() {

            @Override
            public int compare(AccessPoint lhs, AccessPoint rhs) {
                return rhs.mRssi - lhs.mRssi;
            }
        });
    }

}
