/*
 * Copyright 2015 Google Inc. All rights reserved.
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

package com.rtx.treadmill.BT;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.RtxMainFunction.Login.LoginProc;
import com.rtx.treadmill.RtxMainFunction.Login.LoginState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BT_login_Peripheral {
    private static String TAG = "Tom=BT";
    private static boolean DEBUG = true;

    private static final UUID CLIENT_CHARACTERISTIC_CONFIGURATION_UUID = UUID
          .fromString("00002902-0000-1000-8000-00805f9b34fb");

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGattService mBluetoothGattService;
    private HashSet<BluetoothDevice> mBluetoothDevices;
    private BluetoothLeAdvertiser mAdvertiser;

    private BluetoothGattServer mGattServer;
    private AdvertiseData mAdvData;
    private AdvertiseData mAdvScanResponse;
    private AdvertiseSettings mAdvSettings;
    private BTGattService mCurrentService = null;

//    private CloudCmd cloudcmd;

    BluetoothListener BTlistener = null;

    private Context mContext = null ;
    private Handler mhandler;
    private boolean b_receive = true;
    private boolean b_email = false;
    private boolean b_password = false;
    private boolean b_Confirm = false;
    private boolean b_QuickLoginShow = false;
    private int i_Runcount = 0;
    private int i_receive_len = 0;
    private byte[] breceive = new byte[512];

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_BT_ON = 0x0304;
    public static final int MESSAGE_BT_OFF = 0x0305;
    public static final int MESSAGE_BT_SERVER = 0x0306;

    private LoginProc loginProc;

    public BT_login_Peripheral(Context mContext, LoginProc loginProc)
    {
        this.mContext = mContext;
        this.loginProc = loginProc;
    }

    private final AdvertiseCallback mAdvCallback = new AdvertiseCallback() {
        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            if(DEBUG) Log.e(TAG, "=====onStartFailure=" + errorCode);
            int statusText;
            switch (errorCode) {
                case ADVERTISE_FAILED_ALREADY_STARTED:
                    if(DEBUG) Log.w(TAG, "App was already advertising");
                    break;
                case ADVERTISE_FAILED_DATA_TOO_LARGE:
                    break;
                case ADVERTISE_FAILED_FEATURE_UNSUPPORTED:
                    break;
                case ADVERTISE_FAILED_INTERNAL_ERROR:
                    break;
                case ADVERTISE_FAILED_TOO_MANY_ADVERTISERS:
                    break;
                default:
                    if(DEBUG) Log.e(TAG, "Unhandled error: " + errorCode);
                    break;
            }
        }

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            if(DEBUG) Log.e(TAG, "======onStartSuccess======");
        }
    };

    private final BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, final int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            if(DEBUG) Log.e(TAG, "===========onConnectionStateChange==status=" + status + "     newState=" + newState);

            //loginProc.vShowQuickLogin();

            if (status == BluetoothGatt.GATT_SUCCESS)
            {
                if (newState == BluetoothGatt.STATE_CONNECTED)
                {
                    loginProc.vShowQuickLogin();
                    b_QuickLoginShow = true;
                    mBluetoothDevices.add(device);
//                    global.v_set_error_state(0xB4);

                }
                else if (newState == BluetoothGatt.STATE_DISCONNECTED)
                {
                    //loginProc.vShowQuickLoginFail();
                    //b_QuickLoginShow = false;
                    mBluetoothDevices.remove(device);
//                    global.v_Set_error_info(0, global.ierror_timeout_defalt, global.Main_Status.ERROR_DEL);
                }
            }
            else
            {
                mBluetoothDevices.remove(device);
            }
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset,
        BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            if(DEBUG) Log.e(TAG, "==========onCharacteristicReadRequest" + characteristic.getUuid());
            if (offset != 0) {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, offset,
                /* value (optional) */ null);
                return;
            }
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
            offset, characteristic.getValue());
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            super.onNotificationSent(device, status);
            if(DEBUG) Log.e(TAG, "Notification sent. Status: " + status);
        }

        private void v_login_from_bt()
        {
            Log.e("Jerry","gbt.semail = " + gbt.semail);
            Log.e("Jerry","gbt.spassword = " + gbt.spassword);

            loginProc.vSetLoginData(gbt.semail,gbt.spassword);
            loginProc.vSetNextState(LoginState.PROC_CLOUD_LOGIN);

//            int iresult = 0x00;
//
            b_receive = false;
            b_email = false;
            b_password = false;
//
//            if (global.i_check_account(gbt.semail) == 0) {
//                if (global.i_check_password(gbt.spassword) != 0) {
//                    iresult = 0x01;
//                }
//                else
//                {
//                    global.semail = gbt.semail;
//                    global.spassword = gbt.spassword;
//                    if(global.i_check_netstatus() != 0)
//                    {
//                        iresult = 0x40;
//                    }
//                    else
//                    {
//                        iresult = 0xB1;
//                    }
//                }
//            }
//            global.v_set_error_state(iresult);
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId,
        BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded,
        int offset, byte[] value) {
            //super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite,
            //    responseNeeded, offset, value);
            if(DEBUG) Log.e(TAG, "=============characteristic=" + responseNeeded);
            if (responseNeeded) {
                if (DEBUG) Log.e(TAG, "==============responseNeeded=" + characteristic.getUuid());
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
                /* No need to respond with an offset */ 0,
                /* No need to respond with a value */ null);
            }

            i_Runcount = 0;
//            global.v_Clear_Timeout_Count();

            if(!b_receive)
            {
                return;
            }

            int ilen = value.length;
            int iLoop;
            Log.e(TAG, "ilen=" + ilen);
            Log.e(TAG, "Characteristic Write request: " + Arrays.toString(value));

            for (iLoop = 0; iLoop < ilen; iLoop++) {
                breceive[i_receive_len] = value[iLoop];
                i_receive_len++;
            }

            if(value[ilen - 2] == 0x0D && value[ilen - 1] == 0x0A)
            {
                if (breceive[0] == 'F' && breceive[1] == '0' && breceive[2] == '6') {//email
                    gbt.semail = new String(breceive, 3, i_receive_len - 5);
                    b_email = true;
                    sendMessage(guart.SET_EMAIL, guart.bOK);
                    Log.e(TAG, "gbt.semail=" + gbt.semail);
                }
                else if (breceive[0] == 'F' && breceive[1] == '0' && breceive[2] == '7') {//password
                    gbt.spassword = new String(breceive, 3, i_receive_len - 5);
                    b_password = true;
                    sendMessage(guart.SET_PASSWORD, guart.bOK);
                    Log.e(TAG, "gbt.spassword=" + gbt.spassword);
                }
                else if(breceive[0] == 'F' && breceive[1] == '1' && breceive[2] == '1' && breceive[3] == 'W' && breceive[4] == 'H' && breceive[5] == 'O')
                {
                    byte bsent ;
                    bsent = guart.machine_type_T;
                    sendMessage_type(guart.GET_MACHINE_TYPE, bsent);
                }
                else if(breceive[0] == 'N' && breceive[1] == 'E' && breceive[2] == 'W')
                {
                    b_Confirm = true;
                }
                else
                {
                    sendMessage(guart.SET_EMAIL, guart.bNG);
                }
                i_receive_len = 0;
            }

            if(b_email && b_password) {
                v_login_from_bt();
            }
        }

        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId,
        int offset, BluetoothGattDescriptor descriptor) {
            super.onDescriptorReadRequest(device, requestId, offset, descriptor);
            if(DEBUG) Log.e(TAG, "Device tried to read descriptor: " + descriptor.getUuid());
            if(DEBUG) Log.e(TAG, "Value: " + Arrays.toString(descriptor.getValue()));
            if (offset != 0) {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, offset,
                /* value (optional) */ null);
                return;
            }
            mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset,
            descriptor.getValue());
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId,
        BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded,
        int offset, byte[] value) {
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded,
            offset, value);
            if(DEBUG) Log.e(TAG, "Descriptor Write Request " + descriptor.getUuid() + " " + Arrays.toString(value));
            int status = BluetoothGatt.GATT_SUCCESS;
            if (descriptor.getUuid() == CLIENT_CHARACTERISTIC_CONFIGURATION_UUID) {
                BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
                boolean supportsNotifications = (characteristic.getProperties() &
                BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0;
                boolean supportsIndications = (characteristic.getProperties() &
                BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0;

                if (!(supportsNotifications || supportsIndications)) {
                    status = BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED;
                } else if (value.length != 2) {
                    status = BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH;
                } else if (Arrays.equals(value, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)) {
                    status = BluetoothGatt.GATT_SUCCESS;
                    descriptor.setValue(value);
                } else if (supportsNotifications &&
                    Arrays.equals(value, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)) {
                    status = BluetoothGatt.GATT_SUCCESS;
                    descriptor.setValue(value);
                } else if (supportsIndications &&
                    Arrays.equals(value, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)) {
                    status = BluetoothGatt.GATT_SUCCESS;
                    descriptor.setValue(value);
                } else {
                    status = BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED;
                }
            } else {
                status = BluetoothGatt.GATT_SUCCESS;
                descriptor.setValue(value);
            }
            if (responseNeeded) {
                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS,
                /* No need to respond with offset */ 0,
                /* No need to respond with a value */ null);
            }
        }
    };

    private void sendMessage(int cmd, byte bresult) {
        // Check that we're actually connected before trying anything
        byte[] send = {0x52, 0x30, 0x36, 0x1F, 0x0D, 0x0A};
        send[3] = bresult;
        // Check that there's actually something to send
        if (cmd == guart.SET_EMAIL) {
            send[2] = 0x36;
        }
        else  if (cmd == guart.SET_PASSWORD) {
            send[2] = 0x37;
        }
        Log.e(TAG, "=========sendMessage====type===" + Arrays.toString(send));
        mCurrentService.getmNotifyCharacteristic().setValue(send);
        for (BluetoothDevice device : mBluetoothDevices) {
            if(DEBUG) Log.e(TAG, "===========sendNotificationToDevices===device=" + device);
            mGattServer.notifyCharacteristicChanged(device, mCurrentService.getmNotifyCharacteristic(), false);
        }
    }

    private void sendMessage_type(int cmd, byte bresult) {
        // Check that we're actually connected before trying anything
        byte[] send = {0x52, 0x31, 0x31, 0x30, 0x0D, 0x0A};
        // Check that there's actually something to send
        send[3] = bresult;

        if(DEBUG) Log.e(TAG, "=========sendMessage====type===" + Arrays.toString(send));
        mCurrentService.getmNotifyCharacteristic().setValue(send);
        for (BluetoothDevice device : mBluetoothDevices) {
            if(DEBUG) Log.e(TAG, "===========sendNotificationToDevices===device=" + device);
            mGattServer.notifyCharacteristicChanged(device, mCurrentService.getmNotifyCharacteristic(), false);
        }

    }
    /////////////////////////////////
    ////// Lifecycle Callbacks //////
    /////////////////////////////////
    public void onStart(Context context) {
        if(DEBUG) Log.e(TAG, "=======onStart=====");
//        mContext = context;
        mhandler = new myHandler(Looper.getMainLooper());

        BTlistener = new BluetoothListener();
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(BTlistener, filter);

        mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothDevices = new HashSet<>();

        //this.cloudcmd = cloudcmd;
        b_QuickLoginShow = false;

        if (!mBluetoothAdapter.isEnabled()) {
            mhandler.sendEmptyMessage(MESSAGE_BT_ON);
        }
        else
        {
            Start_BT_Device();
        }
        b_receive = true;
        b_email = false;
        b_password = false;
        b_Confirm = false;
    }

    public void onStop() {
        if(DEBUG) Log.e(TAG, "========onStop==========");
        Stop_BT_Device();
    }

    public void v_timeout_count() {
        i_Runcount++;
        if(i_Runcount / EngSetting.DEF_SEC_COUNT >= 10)
        {
            Log.e(TAG, "========BT timeout==========");
            i_Runcount = 0;
            b_receive = true;
            b_email = false;
            b_password = false;
            b_Confirm = false;
            i_receive_len = 0;

            if(b_QuickLoginShow)
            {
                loginProc.vShowQuickLoginFail();
                b_QuickLoginShow = false;
            }

            if(!mBluetoothAdapter.isEnabled())
            {
                mhandler.sendEmptyMessage(MESSAGE_BT_ON);
            }
        }
    }

    private class BluetoothListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mIntent = intent.getAction();
            if(mIntent == BluetoothAdapter.ACTION_STATE_CHANGED) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                int pre_state = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1);
                String msg = null;
                switch (pre_state) {
                    case BluetoothAdapter.STATE_TURNING_ON:
                        msg = "turning on";
                        break;
                    case BluetoothAdapter.STATE_ON:
                        msg = "on";
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        msg = "turning off";
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        msg = "off";
                    break;
                }
                if (DEBUG) Log.e(TAG, "BluetoothStateListener pre_state=" + msg);

                switch (state) {
                    case BluetoothAdapter.STATE_TURNING_ON:
                        msg = "turning on";
                        break;
                    case BluetoothAdapter.STATE_ON:
                        mhandler.sendEmptyMessage(MESSAGE_BT_SERVER);
                        //mhandler.sendEmptyMessageDelayed(MESSAGE_BT_SERVER,1000);
                        msg = "on";
                    break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                        msg = "turning off";
                    break;
                        case BluetoothAdapter.STATE_OFF:
                        msg = "off";
                    break;
                }
                if (DEBUG) Log.e(TAG, "BluetoothStateListener state=" + msg);
            }
            else if(mIntent == BluetoothDevice.ACTION_FOUND)
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 这里可以把我们的找到的设备添加到一个列表中
                if(DEBUG)Log.e(TAG,"GetScanDevice===" + device.getName() + " : " + device.getAddress());
            }
        }
    }

    private void Start_BT_Device()
    {
        if(DEBUG) Log.e(TAG, "=======Start_BT_Device=====");
        mGattServer = mBluetoothManager.openGattServer(mContext, mGattServerCallback);

        if (mCurrentService == null) {
            mCurrentService = new BTGattService();
        }

        if (mCurrentService != null) {
            mAdvSettings = new AdvertiseSettings.Builder()
                  .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                  .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                  .setConnectable(true)
                  .build();

            mAdvData = new AdvertiseData.Builder()
                  .setIncludeTxPowerLevel(true)
                  .addServiceUuid(mCurrentService.getServiceUUID())
                  .build();


            mAdvScanResponse = new AdvertiseData.Builder()
                  .setIncludeDeviceName(true)
                  .build();
            mBluetoothGattService = mCurrentService.getBluetoothGattService();

        }
        else
        {
            if(DEBUG) Log.e(TAG, "=======Start_BT_Device=======mCurrentService=null");
            return;
        }

        if(mGattServer != null) {
            mGattServer.addService(mBluetoothGattService);
        }
        else
        {
            if(DEBUG) Log.e(TAG, "=======Start_BT_Device=======mGattServer=null");
            return;
        }

        if (mBluetoothAdapter.isMultipleAdvertisementSupported()) {
            if(DEBUG) Log.e(TAG, "=======Start_BT_Device=======MultipleAdvertisementSupported");
            mAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
            if(mAdvertiser != null) {
                mAdvertiser.startAdvertising(mAdvSettings, mAdvData, mAdvScanResponse, mAdvCallback);
            }
            else
            {
                if(DEBUG) Log.e(TAG, "=======Start_BT_Device=======mAdvertiser=null");
            }
        }
    }

    private void Stop_BT_Device()
    {
        if(DEBUG) Log.e(TAG, "========Stop_BT_Device======");
        if (mGattServer != null) {
            mGattServer.close();
        }

        if (mBluetoothAdapter.isEnabled() && mAdvertiser != null) {
            mAdvertiser.stopAdvertising(mAdvCallback);
        }

        disconnectFromDevices();
        if(BTlistener != null) {
            mContext.unregisterReceiver(BTlistener);
            BTlistener = null;
        }

        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }

        b_receive = true;
    }

    public void sendNotificationToDevices(BluetoothGattCharacteristic characteristic) {
        if(DEBUG) Log.e(TAG, "========sendNotificationToDevices======");
        boolean indicate = (characteristic.getProperties()
            & BluetoothGattCharacteristic.PROPERTY_INDICATE)
            == BluetoothGattCharacteristic.PROPERTY_INDICATE;
        for (BluetoothDevice device : mBluetoothDevices) {
            // true for indication (acknowledge) and false for notification (unacknowledge).
            mGattServer.notifyCharacteristicChanged(device, characteristic, indicate);
        }
    }

    ///////////////////////
    ////// Bluetooth //////
    ///////////////////////
    public static BluetoothGattDescriptor getClientCharacteristicConfigurationDescriptor() {
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_UUID,
            (BluetoothGattDescriptor.PERMISSION_READ | BluetoothGattDescriptor.PERMISSION_WRITE));
        descriptor.setValue(new byte[]{0, 0});
        if(DEBUG) Log.e(TAG, "========getClientCharacteristicConfigurationDescriptor======descriptor===" + descriptor);
        return descriptor;
    }

    private void disconnectFromDevices() {
        if(DEBUG) Log.e(TAG, "Disconnecting devices...");

        loginProc.vDismissInfoDialog();

        for (BluetoothDevice device : mBluetoothManager.getConnectedDevices(BluetoothGattServer.GATT)) {
            if(DEBUG) Log.e(TAG, "Devices: " + device.getAddress() + " " + device.getName());
            mGattServer.cancelConnection(device);
        }
    }

    // The Handler that gets information back from the BluetoothChatService
    protected class myHandler extends Handler {
        public myHandler(Looper looper) {
      super(looper);
    }

        public void handleMessage(Message msg) {
            if(DEBUG) Log.e(TAG, "===msg.what===" + msg);
            switch (msg.what) {
            case MESSAGE_BT_ON:
                if(DEBUG) Log.e(TAG, "===msg.what===MESSAGE_BT_ON");
                mBluetoothAdapter.enable();
                break;
            case MESSAGE_BT_OFF:
                if(DEBUG) Log.e(TAG, "===msg.what===MESSAGE_BT_OFF");
                mBluetoothAdapter.disable();
                break;
            case MESSAGE_BT_SERVER:
                if(DEBUG) Log.e(TAG, "===msg.what===MESSAGE_BT_SERVER");
                if(mBluetoothAdapter.isEnabled()) {
                    if (DEBUG) Log.e(TAG, "=========mBluetoothAdapter.getName============" + mBluetoothAdapter.getName());
                    if(mBluetoothAdapter.getName().compareTo(gbt.BTname) != 0) {
                        mBluetoothAdapter.setName(gbt.BTname);
                    }
                    Start_BT_Device();
                }
                else
                {
                    mhandler.sendEmptyMessageDelayed(MESSAGE_BT_ON,1000);
                }
                break;
            default:
                break;
            }
        }
    };


}
