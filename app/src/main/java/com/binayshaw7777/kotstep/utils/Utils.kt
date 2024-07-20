package com.binayshaw7777.kotstep.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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
        { Text("Step 1: Introduction") },
        {
            Column {
                Text("Step 2: Personal Info")
                Text("Fill in your details")
            }
        },
        {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Step 5: Review", style = MaterialTheme.typography.bodyLarge)
                    Text("Check your information", style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = { Log.d(this@Utils.toString(), "Button clicked!") }) {
                        Text("Review Now")
                    }
                }
            }
        },
        null,
        null,
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Step 6: Submit", style = MaterialTheme.typography.bodyLarge)
            }
        },
        { Text("Step 1: Introduction") },
        {
            Column {
                Text("Step 2: Personal Info")
                Text("Fill in your details")
            }
        },
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Info",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Step 3: Preferences", style = MaterialTheme.typography.bodyLarge)
            }
        },
        null
    )
        .subList(0, limit)
}

fun Int.toast(context: Context) {
    Toast.makeText(context, "Clicked on item: $this", Toast.LENGTH_SHORT).show()
}