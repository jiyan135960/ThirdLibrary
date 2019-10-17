package com.example.thirdlibrary.leet

import org.junit.Test

/**
 * Created by zyx on 19-10-17.
 */
class VerifyUtf8Unicode {

    /**
    UTF-8 中的一个字符可能的长度为 1 到 4 字节，遵循以下的规则：
    对于 1 字节的字符，字节的第一位设为0，后面7位为这个符号的unicode码。
    对于 n 字节的字符 (n > 1)，第一个字节的前 n 位都设为1，第 n+1 位设为0，后面字节的前两位一律设为10。剩下的没有提及的二进制位，全部为这个符号的unicode码。
    这是 UTF-8 编码的工作方式：

    Char. number range  |        UTF-8 octet sequence
    (hexadecimal)       |              (binary)
    --------------------+---------------------------------------------
    0000 0000-0000 007F | 0xxxxxxx
    0000 0080-0000 07FF | 110xxxxx 10xxxxxx
    0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
    0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxx

    给定一个表示数据的整数数组，返回它是否为有效的 utf-8 编码。

    注意:
    输入是整数数组。只有每个整数的最低 8 个有效位用来存储数据。这意味着每个整数只表示 1 字节的数据。

    示例 1:

    data = [197, 130, 1], 表示 8 位的序列: 11000101 10000010 00000001.

    返回 true 。
    这是有效的 utf-8 编码，为一个2字节字符，跟着一个1字节字符。
    示例 2:

    data = [235, 140, 4], 表示 8 位的序列: 11101011 10001100 00000100.

    返回 false 。
    前 3 位都是 1 ，第 4 位为 0 表示它是一个3字节字符。
    下一个字节是开头为 10 的延续字节，这是正确的。
    但第二个延续字节不以 10 开头，所以是不符合规则的。
     */

    fun validUtf8(data: IntArray): Boolean {
        //确定字节范围
        var charSize = 0
        data.forEach {
            var binary = Integer.toBinaryString(it)
            for (index in binary.length until 8) binary = "0$binary"
            if (charSize == 0) {
                when (val binaryCharSize = binary.toCharArray().takeWhile { it == '1' }.size) {
                    0 -> return@forEach
                    in 2..4 -> {
                        charSize = binaryCharSize - 1
                        return@forEach
                    }
                    else -> return false
                }
            } else {
                if (binary.startsWith("10")) {
                    charSize--
                } else {
                    return false
                }
            }
        }
        return charSize == 0
    }

    fun validUtf8Mask(data: IntArray): Boolean {
        val mask1 = 1.shl(7)
        val mask2 = 1.shl(6)

        var charSize = 0
        data.forEach {
            var internalMask = 1.shl(7)
            if (charSize == 0) {
                while (internalMask.and(it) != 0) {
                    charSize++
                    internalMask = internalMask.shr(1)
                }
                if (charSize == 0) return@forEach
                if (charSize == 1 || charSize > 4) return false
                charSize--
            } else {
                if ((mask1.and(it) != 0) && (mask2.and(it) == 0)) {
                    charSize--
                } else {
                    return false
                }
            }
        }
        return charSize == 0
    }

    @Test
    fun testValidUtf8() {
        //240,162,138,147,17
        print("result: ${validUtf8Mask(intArrayOf(197,130,1))}")
    }
}