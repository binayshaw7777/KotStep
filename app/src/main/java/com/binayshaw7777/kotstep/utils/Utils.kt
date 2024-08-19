package com.binayshaw7777.kotstep.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp

object Utils {

    fun getIcons(limit: Int): List<ImageVector> {
        return listOf(
            Icons.Default.AccountCircle,
            Icons.Default.Place,
            Icons.Default.Favorite,
            Icons.Default.Search,
            Icons.Default.Face,
            Icons.Default.Build,
            Icons.Default.Check,
            Icons.Default.Create,
            Icons.Default.DateRange,
            Icons.Default.MailOutline
        ).subList(0, limit)
    }

    fun getLabels(limit: Int) = listOf<@Composable (() -> Unit)?>(
        { Text("Ordered") },
        {
            Column(verticalArrangement = Arrangement.Top) {
                Text("Shipped")
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
                Text("Reached the facility X.", fontSize = 14.sp)
            }
        },
        {
            Text("Reached the facility Y.", fontSize = 14.sp)
        },
        null,
        null,
        {

            Text("Reached the facility Z", fontSize = 14.sp)
            
        },
        { Text("Out for Delivery", fontSize = 14.sp) },
        {
            Text("Delivery delayed due to rain", fontSize = 14.sp)
        },
        {
            Text(text = "Item delivered.")
        },
        null
    )
        .subList(0, limit)
}

fun Int.toast(context: Context) {
    Toast.makeText(context, "Clicked on item: $this", Toast.LENGTH_SHORT).show()
}