package com.herdal.marketinventory.utils.extensions

import com.herdal.marketinventory.data.local.Item
import java.text.NumberFormat

fun Item.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(this.price)