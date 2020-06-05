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

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;

import java.util.UUID;


public class BTGattService {

  private static final UUID UART_SERVICE_UUID = UUID
          .fromString("0000ffe0-0000-1000-8000-00805f9b34fb");

  private static final UUID UART_NOTIFY_UUID = UUID
          .fromString("0000ffe1-0000-1000-8000-00805f9b34fb");

  private static final UUID UART_WRITE_UUID = UUID
      .fromString("0000ffe2-0000-1000-8000-00805f9b34fb");

  // GATT
  private BluetoothGattService mUARTService;
  public BluetoothGattCharacteristic mUARTNotifyCharacteristic;
  private BluetoothGattCharacteristic mUARTWriteCharacteristic;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public BTGattService() {
    mUARTNotifyCharacteristic =
        new BluetoothGattCharacteristic(UART_NOTIFY_UUID,
            BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            0);

    mUARTNotifyCharacteristic.addDescriptor(
            BT_login_Peripheral.getClientCharacteristicConfigurationDescriptor());

    mUARTWriteCharacteristic =
            new BluetoothGattCharacteristic(UART_WRITE_UUID,
                    BluetoothGattCharacteristic.PROPERTY_WRITE,
                    BluetoothGattCharacteristic.PERMISSION_WRITE);

    mUARTService = new BluetoothGattService(UART_SERVICE_UUID,
        BluetoothGattService.SERVICE_TYPE_PRIMARY);

    mUARTService.addCharacteristic(mUARTNotifyCharacteristic);
    mUARTService.addCharacteristic(mUARTWriteCharacteristic);
  }

  public BluetoothGattService getBluetoothGattService() {
    return mUARTService;
  }

  public ParcelUuid getServiceUUID() {
    return new ParcelUuid(UART_SERVICE_UUID);
  }

  public BluetoothGattCharacteristic getmNotifyCharacteristic() {
    return mUARTNotifyCharacteristic;
  }

}
