package com.pivovarit.hamming.domain.encode.stateless

import com.pivovarit.hamming.domain.BinaryString
import com.pivovarit.hamming.domain.EncodedString
import com.pivovarit.hamming.domain.encode.HammingEncoder
import com.pivovarit.hamming.domain.isPowerOfTwo

internal class SequentialStatelessHammingEncoder : HammingEncoder {

    private val hammingHelper: HammingHelper = HammingHelper()

    override fun encode(input: BinaryString) = hammingHelper.getHammingCodewordIndices(input.length)
      .toList().stream() // to be fair.
      .map { toHammingCodeValue(it, input) }
      .reduce("") { t, u -> t + u }
      .let(::EncodedString)

    private fun toHammingCodeValue(it: Int, input: BinaryString) =
      when ((it + 1).isPowerOfTwo()) {
          true -> hammingHelper.getParityBit(it, input)
          false -> hammingHelper.getDataBit(it, input)
      }
}
