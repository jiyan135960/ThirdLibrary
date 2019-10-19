package com.example.thirdlibrary.leet

import org.junit.Test
import java.util.*

/**
 * Created by zyx on 19-10-18.
 */
class NumberOfStom {
    /**
    给定一个化学式formula（作为字符串），返回每种原子的数量。

    原子总是以一个大写字母开始，接着跟随0个或任意个小写字母，表示原子的名字。

    如果数量大于 1，原子后会跟着数字表示原子的数量。如果数量等于 1 则不会跟数字。例如，H2O 和 H2O2 是可行的，但 H1O2 这个表达是不可行的。

    两个化学式连在一起是新的化学式。例如 H2O2He3Mg4 也是化学式。

    一个括号中的化学式和数字（可选择性添加）也是化学式。例如 (H2O2) 和 (H2O2)3 是化学式。

    给定一个化学式，输出所有原子的数量。格式为：第一个（按字典序）原子的名子，跟着它的数量（如果数量大于 1），然后是第二个原子的名字（按字典序），跟着它的数量（如果数量大于 1），以此类推。

    示例 1:

    输入:
    formula = "H2O"
    输出: "H2O"
    解释:
    原子的数量是 {'H': 2, 'O': 1}。
    示例 2:

    输入:
    formula = "Mg(OH)2"
    输出: "H2MgO2"
    解释:
    原子的数量是 {'H': 2, 'Mg': 1, 'O': 2}。
    示例 3:

    输入:
    formula = "K4(ON(SO3)2)2"
    输出: "K4N2O14S4"
    解释:
    原子的数量是 {'K': 4, 'N': 2, 'O': 14, 'S': 4}。
    注意:

    所有原子的第一个字母为大写，剩余字母都是小写。
    formula的长度在[1, 1000]之间。
    formula只包含字母、数字和圆括号，并且题目中给定的是合法的化学式。
     */
    //A-Z byte:65-90
    //0-9 byte:48-57
    //a-z byte:97-122
    //(-) byte:40-41
    fun countOfAtoms(formula: String): String {
        val formulaChars = formula.toCharArray()
        val result = loopGetString(formulaChars.iterator())
        val resultString = StringBuilder()
        result.forEach {
            if (it.value != 1) {
                resultString.append(it.key)
                resultString.append(it.value)
            } else {
                resultString.append(it.key)
            }
        }
        return resultString.toString()
    }


    fun loopGetString(formulaCharsIterator: Iterator<Char>): TreeMap<String, Int> {
        //当前循环已经分析完成的元素
        val currentElements = TreeMap<String, Int>()

        //正在添加的元素
        val stringBuilder = StringBuilder()
        //当前的倍数
        var currentMulti: String? = null
        //括号内的元素
        val parenthesesElements = TreeMap<String, Int>()
        //循环获取所有字符
        while (formulaCharsIterator.hasNext()) {
            val nextChar = formulaCharsIterator.next()
            when (nextChar.toInt()) {
                in 65..90 -> {
                    calculateAndAddElement(
                        stringBuilder,
                        currentMulti,
                        currentElements,
                        parenthesesElements
                    )
                    currentMulti = null
                    stringBuilder.append(nextChar)
                }
                in 48..57 -> currentMulti = (currentMulti ?: "") + nextChar
                in 97..122 -> stringBuilder.append(nextChar)
                40 -> {
                    calculateAndAddElement(
                        stringBuilder,
                        currentMulti,
                        currentElements,
                        parenthesesElements
                    )
                    currentMulti = null
                    parenthesesElements.putAll(loopGetString(formulaCharsIterator))
                }
                41 -> {
                    calculateAndAddElement(
                        stringBuilder,
                        currentMulti,
                        currentElements,
                        parenthesesElements
                    )
                    return currentElements
                }
            }
        }
        calculateAndAddElement(
            stringBuilder,
            currentMulti,
            currentElements,
            parenthesesElements
        )
        return currentElements
    }

    private fun calculateAndAddElement(
        stringBuilder: StringBuilder,
        currentMulti: String?,
        currentElements: TreeMap<String, Int>,
        parenthesesElements: TreeMap<String, Int>
    ) {
        if (stringBuilder.isNotEmpty()) {
            //生成元素字符串
            val elementStr = stringBuilder.toString()
            stringBuilder.clear()
            //个数判断
            val multi = calculateMulti(currentMulti)
            //已完成元素增加
            val allowAddElements = TreeMap<String, Int>()
            allowAddElements[elementStr] = multi
            addElement2CurrentMap(currentElements, allowAddElements)
        } else if (parenthesesElements.isNotEmpty()) {
            //倍数计算
            val multi = calculateMulti(currentMulti)
            if (multi > 1) parenthesesElementMulti(parenthesesElements, multi)
            //已完成元素增加
            addElement2CurrentMap(currentElements, parenthesesElements)
            parenthesesElements.clear()
        }
    }


    private fun calculateMulti(currentMulti: String?): Int = currentMulti?.toInt() ?: 1

    private fun addElement2CurrentMap(
        currentElements: TreeMap<String, Int>,
        allowAddElements: TreeMap<String, Int>
    ) {
        allowAddElements.forEach {
            currentElements[it.key] = (currentElements[it.key] ?: 0) + it.value
        }
    }

    private fun parenthesesElementMulti(
        parenthesesElements: TreeMap<String, Int>,
        multi: Int
    ) {
        parenthesesElements.forEach {
            parenthesesElements[it.key] = it.value * multi
        }
    }

    //"H11He49NO35B7N46Li20"
    @Test
    fun main() {
        //"B18900Be18984C4200H5446He1386Li33894N50106O22638"
        print("result : ${countOfAtoms("B18900Be18984C4200H5446He1386Li33894N50106O22638")}")
    }
}