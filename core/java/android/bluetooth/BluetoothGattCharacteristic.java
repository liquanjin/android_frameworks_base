/*
 * Copyright (C) 2013 The Android Open Source Project
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
package android.bluetooth;

import java.util.ArrayList;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Bluetooth Gatt Characteristic
 * @hide
 */
public class BluetoothGattCharacteristic {

    /**
     * Characteristic proprty: Characteristic is broadcastable.
     */
    public static final int PROPERTY_BROADCAST = 0x01;

    /**
     * Characteristic property: Characteristic is readable.
     */
    public static final int PROPERTY_READ = 0x02;

    /**
     * Characteristic property: Characteristic can be written without response.
     */
    public static final int PROPERTY_WRITE_NO_RESPONSE = 0x04;

    /**
     * Characteristic property: Characteristic can be written.
     */
    public static final int PROPERTY_WRITE = 0x08;

    /**
     * Characteristic property: Characteristic supports notification
     */
    public static final int PROPERTY_NOTIFY = 0x10;

    /**
     * Characteristic property: Characteristic supports indication
     */
    public static final int PROPERTY_INDICATE = 0x20;

    /**
     * Characteristic property: Characteristic supports write with signature
     */
    public static final int PROPERTY_SIGNED_WRITE = 0x40;

    /**
     * Characteristic property: Characteristic has extended properties
     */
    public static final int PROPERTY_EXTENDED_PROPS = 0x80;

    /**
     * Characteristic read permission
     */
    public static final int PERMISSION_READ = 0x01;

    /**
     * Characteristic permission: Allow encrypted read operations
     */
    public static final int PERMISSION_READ_ENCRYPTED = 0x02;

    /**
     * Characteristic permission: Allow reading with man-in-the-middle protection
     */
    public static final int PERMISSION_READ_ENCRYPTED_MITM = 0x04;

    /**
     * Characteristic write permission
     */
    public static final int PERMISSION_WRITE = 0x10;

    /**
     * Characteristic permission: Allow encrypted writes
     */
    public static final int PERMISSION_WRITE_ENCRYPTED = 0x20;

    /**
     * Characteristic permission: Allow encrypted writes with man-in-the-middle
     * protection
     */
    public static final int PERMISSION_WRITE_ENCRYPTED_MITM = 0x40;

    /**
     * Characteristic permission: Allow signed write operations
     */
    public static final int PERMISSION_WRITE_SIGNED = 0x80;

    /**
     * Characteristic permission: Allow signed write operations with
     * man-in-the-middle protection
     */
    public static final int PERMISSION_WRITE_SIGNED_MITM = 0x100;

    /**
     * Write characteristic, requesting acknoledgement by the remote device
     */
    public static final int WRITE_TYPE_DEFAULT = 0x02;

    /**
     * Wrtite characteristic without requiring a response by the remote device
     */
    public static final int WRITE_TYPE_NO_RESPONSE = 0x01;

    /**
     * Write characteristic including and authenticated signature
     */
    public static final int WRITE_TYPE_SIGNED = 0x04;

    /**
     * Characteristic value format type uint8
     */
    public static final int FORMAT_UINT8 = 0x11;

    /**
     * Characteristic value format type uint16
     */
    public static final int FORMAT_UINT16 = 0x12;

    /**
     * Characteristic value format type uint32
     */
    public static final int FORMAT_UINT32 = 0x14;

    /**
     * Characteristic value format type sint8
     */
    public static final int FORMAT_SINT8 = 0x21;

    /**
     * Characteristic value format type sint16
     */
    public static final int FORMAT_SINT16 = 0x22;

    /**
     * Characteristic value format type sint32
     */
    public static final int FORMAT_SINT32 = 0x24;

    /**
     * Characteristic value format type sfloat (16-bit float)
     */
    public static final int FORMAT_SFLOAT = 0x32;

    /**
     * Characteristic value format type float (32-bit float)
     */
    public static final int FORMAT_FLOAT = 0x34;


    /**
     * The UUID of this characteristic.
     * @hide
     */
    protected UUID mUuid;

    /**
     * Instance ID for this characteristic.
     * @hide
     */
    protected int mInstance;

    /**
     * Characteristic properties.
     * @hide
     */
    protected int mProperties;

    /**
     * Characteristic permissions.
     * @hide
     */
    protected int mPermissions;

    /**
     * Key size (default = 16).
     * @hide
     */
    protected int mKeySize = 16;

    /**
     * Write type for this characteristic.
     * See WRITE_TYPE_* constants.
     * @hide
     */
    protected int mWriteType;

    /**
     * Back-reference to the service this characteristic belongs to.
     * @hide
     */
    protected BluetoothGattService mService;

    /**
     * The cached value of this characteristic.
     * @hide
     */
    protected byte[] mValue;

    /**
     * List of descriptors included in this characteristic.
     */
    protected List<BluetoothGattDescriptor> mDescriptors;

    /**
     * Create a new BluetoothGattCharacteristic
     * @hide
     */
    /*package*/ BluetoothGattCharacteristic(BluetoothGattService service,
                                            UUID uuid, int instanceId,
                                            int properties, int permissions) {
        mUuid = uuid;
        mInstance = instanceId;
        mProperties = properties;
        mPermissions = permissions;
        mService = service;
        mValue = null;
        mDescriptors = new ArrayList<BluetoothGattDescriptor>();

        if ((mProperties & PROPERTY_WRITE_NO_RESPONSE) != 0) {
            mWriteType = WRITE_TYPE_NO_RESPONSE;
        } else {
            mWriteType = WRITE_TYPE_DEFAULT;
        }
    }

    /**
     * Returns the deisred key size.
     * @hide
     */
    /*package*/ int getKeySize() {
        return mKeySize;
    }

    /**
     * Add a descriptor to this characteristic
     * @hide
     */
    /*package*/ void addDescriptor(BluetoothGattDescriptor descriptor) {
        mDescriptors.add(descriptor);
    }

    /**
     * Returns the service this characteristic belongs to.
     * @return The asscociated service
     */
    public BluetoothGattService getService() {
        return mService;
    }

    /**
     * Returns the UUID of this characteristic
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return UUID of this characteristic
     */
    public UUID getUuid() {
        return mUuid;
    }

    /**
     * Returns the instance ID for this characteristic.
     *
     * <p>If a remote device offers multiple characteristics with the same UUID,
     * the instance ID is used to distuinguish between characteristics.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return Instance ID of this characteristic
     */
    public int getInstanceId() {
        return mInstance;
    }

    /**
     * Returns the properties of this characteristic.
     *
     * <p>The properties contain a bit mask of property flags indicating
     * the features of this characteristic.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return Properties of this characteristic
     */
    public int getProperties() {
        return mProperties;
    }

    /**
     * Returns the permissions for this characteristic.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return Permissions of this characteristic
     */
    public int getPermissions() {
        return mPermissions;
    }

    /**
     * Gets the write type for this characteristic.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return Write type for this characteristic
     */
    public int getWriteType() {
        return mWriteType;
    }

    /**
     * Set the write type for this characteristic
     *
     * <p>Setting the write type of a characteristic determines how the
     * {@link BluetoothGatt#writeCharacteristic} function write this
     * characteristic.
     *
     * <p>The default write type for a characteristic is
     * {@link #WRITE_TYPE_DEFAULT}.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @param writeType The write type to for this characteristic. Can be one
     *                  of:
     *                  {@link #WRITE_TYPE_DEFAULT},
     *                  {@link #WRITE_TYPE_NO_RESPONSE} or
     *                  {@link #WRITE_TYPE_SIGNED}.
     */
    public void setWriteType(int writeType) {
        mWriteType = writeType;
    }

    /**
     * Returns a list of descriptors for this characteristic.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return Descriptors for this characteristic
     */
    public List<BluetoothGattDescriptor> getDescriptors() {
        return mDescriptors;
    }

    /**
     * Returns a descriptor with a given UUID out of the list of
     * descriptors for this characteristic.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return Gatt descriptor object or null if no descriptor with the
     *         given UUID was found.
     */
    public BluetoothGattDescriptor getDescriptor(UUID uuid) {
        for(BluetoothGattDescriptor descriptor : mDescriptors) {
            if (descriptor.getUuid().equals(uuid)) {
                return descriptor;
            }
        }
        return null;
    }

    /**
     * Get the stored value for this characteristic.
     *
     * <p>This function returns the stored value for this characteristic as
     * retrieved by calling {@link BluetoothGatt#readCharacteristic}. To cached
     * value of the characteristic is updated as a result of a read characteristic
     * operation or if a characteristic update notification has been received.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @return Cached value of the characteristic
     */
    public byte[] getValue() {
        return mValue;
    }

    /**
     * Return the stored value of this characteristic.
     *
     * <p>The formatType parameter determines how the characteristic value
     * is to be interpreted. For example, settting formatType to
     * {@link #FORMAT_UINT16} specifies that the first two bytes of the
     * characteristic value at the given offset are interpreted to generate the
     * return value.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @param offset Offset at which the integer value can be found.
     * @return Cached value of the characteristic or null of offset exceeds
     *         value size.
     */
    public Integer getIntValue(int formatType, int offset) {
        if ((offset + getTypeLen(formatType)) > mValue.length) return null;

        switch (formatType) {
            case FORMAT_UINT8:
                return unsignedByteToInt(mValue[offset]);

            case FORMAT_UINT16:
                return unsignedBytesToInt(mValue[offset], mValue[offset+1]);

            case FORMAT_UINT32:
                return unsignedBytesToInt(mValue[offset],   mValue[offset+1],
                                          mValue[offset+2], mValue[offset+3]);
            case FORMAT_SINT8:
                return unsignedToSigned(unsignedByteToInt(mValue[offset]), 8);

            case FORMAT_SINT16:
                return unsignedToSigned(unsignedBytesToInt(mValue[offset],
                                                           mValue[offset+1]), 16);

            case FORMAT_SINT32:
                return unsignedToSigned(unsignedBytesToInt(mValue[offset],
                        mValue[offset+1], mValue[offset+2], mValue[offset+3]), 32);
        }

        return null;
    }

    /**
     * Return the stored value of this characteristic.
     * <p>See {@link #getValue} for details.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @param offset Offset at which the float value can be found.
     * @return Cached value of the characteristic at a given offset or null
     *         if the requested offset exceeds the value size.
     */
    public Float getFloatValue(int formatType, int offset) {
        if ((offset + getTypeLen(formatType)) > mValue.length) return null;

        switch (formatType) {
            case FORMAT_SFLOAT:
                return bytesToFloat(mValue[offset], mValue[offset+1]);

            case FORMAT_FLOAT:
                return bytesToFloat(mValue[offset],   mValue[offset+1],
                                    mValue[offset+2], mValue[offset+3]);
        }

        return null;
    }

    /**
     * Return the stored value of this characteristic.
     * <p>See {@link #getValue} for details.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     * @param offset Offset at which the string value can be found.
     * @return Cached value of the characteristic
     */
    public String getStringValue(int offset) {
        if (offset > mValue.length) return null;
        byte[] strBytes = new byte[mValue.length - offset];
        for (int i=0; i != (mValue.length-offset); ++i) strBytes[i] = mValue[offset+i];
        return new String(strBytes);
    }

    /**
     * Updates the locally stored value of this characteristic.
     *
     * <p>This function modifies the locally stored cached value of this
     * characteristic. To send the value to the remote device, call
     * {@link BluetoothGatt#writeCharacteristic} to send the value to the
     * remote device.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @param value New value for this characteristic
     * @return true if the locally stored value has been set, false if the
     *              requested value could not be stored locally.
     */
    public boolean setValue(byte[] value) {
        mValue = value;
        return true;
    }

    /**
     * Set the locally stored value of this characteristic.
     * <p>See {@link #setValue(byte[])} for details.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     *
     * @param value New value for this characteristic
     * @param formatType Integer format type used to transform the value parameter
     * @param offset Offset at which the value should be placed
     * @return true if the locally stored value has been set
     */
    public boolean setValue(int value, int formatType, int offset) {
        int len = offset + getTypeLen(formatType);
        if (mValue == null) mValue = new byte[len];
        if (len > mValue.length) return false;

        switch (formatType) {
            case FORMAT_SINT8:
                value = intToSignedBits(value, 8);
                // Fall-through intended
            case FORMAT_UINT8:
                mValue[offset] = (byte)(value & 0xFF);
                break;

            case FORMAT_SINT16:
                value = intToSignedBits(value, 16);
                // Fall-through intended
            case FORMAT_UINT16:
                mValue[offset++] = (byte)(value & 0xFF);
                mValue[offset] = (byte)((value >> 8) & 0xFF);
                break;

            case FORMAT_SINT32:
                value = intToSignedBits(value, 32);
                // Fall-through intended
            case FORMAT_UINT32:
                mValue[offset++] = (byte)(value & 0xFF);
                mValue[offset++] = (byte)((value >> 8) & 0xFF);
                mValue[offset] = (byte)((value >> 16) & 0xFF);
                break;

            default:
                return false;
        }
        return true;
    }

    /**
     * Set the locally stored value of this characteristic.
     * <p>See {@link #setValue(byte[])} for details.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     * @param mantissa Mantissa for this characteristic
     * @param exponent  exponent value for this characteristic
     * @param formatType Float format type used to transform the value parameter
     * @param offset Offset at which the value should be placed
     * @return true if the locally stored value has been set
     */
    public boolean setValue(int mantissa, int exponent, int formatType, int offset) {
        int len = offset + getTypeLen(formatType);
        if (mValue == null) mValue = new byte[len];
        if (len > mValue.length) return false;

        switch (formatType) {
            case FORMAT_SFLOAT:
                mantissa = intToSignedBits(mantissa, 12);
                exponent = intToSignedBits(exponent, 4);
                mValue[offset++] = (byte)(mantissa & 0xFF);
                mValue[offset] = (byte)((mantissa >> 8) & 0x0F);
                mValue[offset] += (byte)((exponent & 0x0F) << 4);
                break;

            case FORMAT_FLOAT:
                mantissa = intToSignedBits(mantissa, 24);
                exponent = intToSignedBits(exponent, 8);
                mValue[offset++] = (byte)(mantissa & 0xFF);
                mValue[offset++] = (byte)((mantissa >> 8) & 0xFF);
                mValue[offset++] = (byte)((mantissa >> 16) & 0xFF);
                mValue[offset] += (byte)(exponent & 0xFF);
                break;

            default:
                return false;
        }

        return true;
    }

    /**
     * Set the locally stored value of this characteristic.
     * <p>See {@link #setValue(byte[])} for details.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} permission.
     * @param value New value for this characteristic
     * @return true if the locally stored value has been set
     */
    public boolean setValue(String value) {
        mValue = value.getBytes();
        return true;
    }

    /**
     * Returns the size of a give value type.
     * @hide
     */
    private int getTypeLen(int formatType) {
        return formatType & 0xF;
    }

    /**
     * Convert a signed byte to an unsigned int.
     * @hide
     */
    private int unsignedByteToInt(byte b) {
        return b & 0xFF;
    }

    /**
     * Convert signed bytes to a 16-bit unsigned int.
     * @hide
     */
    private int unsignedBytesToInt(byte b0, byte b1) {
        return (unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8));
    }

    /**
     * Convert signed bytes to a 32-bit unsigned int.
     * @hide
     */
    private int unsignedBytesToInt(byte b0, byte b1, byte b2, byte b3) {
        return (unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8))
             + (unsignedByteToInt(b2) << 16) + (unsignedByteToInt(b3) << 24);
    }

    /**
     * Convert signed bytes to a 16-bit short float value.
     * @hide
     */
    private float bytesToFloat(byte b0, byte b1) {
        int mantissa = unsignedToSigned(unsignedByteToInt(b0)
                        + ((unsignedByteToInt(b1) & 0x0F) << 8), 12);
        int exponent = unsignedToSigned(unsignedByteToInt(b1) >> 4, 4);
        return (float)(mantissa * Math.pow(10, exponent));
    }

    /**
     * Convert signed bytes to a 32-bit short float value.
     * @hide
     */
    private float bytesToFloat(byte b0, byte b1, byte b2, byte b3) {
        int mantissa = unsignedToSigned(unsignedByteToInt(b0)
                        + (unsignedByteToInt(b1) << 8)
                        + (unsignedByteToInt(b2) << 16), 24);
        return (float)(mantissa * Math.pow(10, b3));
    }

    /**
     * Convert an unsigned integer value to a two's-complement encoded
     * signed value.
     * @hide
     */
    private int unsignedToSigned(int unsigned, int size) {
        if ((unsigned & (1 << size-1)) != 0) {
            unsigned = -1 * ((1 << size-1) - (unsigned & ((1 << size-1) - 1)));
        }
        return unsigned;
    }

    /**
     * Convert an integer into the signed bits of a given length.
     * @hide
     */
    private int intToSignedBits(int i, int size) {
        if (i < 0) {
            i = (1 << size-1) + (i & ((1 << size-1) - 1));
        }
        return i;
    }
}
