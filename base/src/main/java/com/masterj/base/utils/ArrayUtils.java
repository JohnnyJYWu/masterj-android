package com.masterj.base.utils;

import java.lang.reflect.Array;

public class ArrayUtils {

    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /**
     * <p>Checks if an array of Objects is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive longs is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(long[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive ints is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(int[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive shorts is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(short[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive chars is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(char[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive bytes is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(byte[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive doubles is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(double[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive floats is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(float[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p>Checks if an array of primitive booleans is empty or <code>null</code>.</p>
     *
     * @param array the array to test
     * @return <code>true</code> if the array is empty or <code>null</code>
     * @since 2.1
     */
    public static boolean isEmpty(boolean[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    public static Integer[] toIntegerArray(int[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; ++i) {
            result[i] = array[i];
        }
        return result;
    }

    public static int[] toIntArray(Integer[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            result[i] = array[i];
        }
        return result;
    }

    public static int indexOf(int[] array, int value) {
        if (isEmpty(array)) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(Object[] array, Object value) {
        if (isEmpty(array)) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (value == null) {
                if (array[i] == null) {
                    return i;
                }
            } else if (value.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(int[] array, int value) {
        return indexOf(array, value) >= 0;
    }

    public static boolean contains(Object[] array, Object value) {
        return indexOf(array, value) >= 0;
    }

    public static int[] append(int[] array, int value) {
        if (isEmpty(array)) {
            return new int[]{value};
        }
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    public static int[] remove(int[] array, int start, int len) {
        if (array.length < len + start) {
            return array;
        }
        int[] r = new int[array.length - len];
        System.arraycopy(array, 0, r, 0, start);
        System.arraycopy(array, start + len, r, start, array.length - start - len);
        return r;
    }

    public static <T> T[] remove(T[] array, int start, int len, Class<T> clazz) {
        if (array.length < len + start) {
            return array;
        }
        T[] r = (T[]) Array.newInstance(clazz, array.length - len);
        System.arraycopy(array, 0, r, 0, start);
        System.arraycopy(array, start + len, r, start, array.length - start - len);
        return r;
    }
}
