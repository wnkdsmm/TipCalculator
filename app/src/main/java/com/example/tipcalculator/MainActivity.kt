package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun DemoSlider(
    sliderPosition: Int,
    onPositionChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Slider(
        modifier = modifier.padding(10.dp),
        valueRange = 0f..25f,
        value = sliderPosition.toFloat(),
        onValueChange = { onPositionChange(it.toInt()) }
    )
}

@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var sliderPosition by remember { mutableIntStateOf(0) }
    val handlePositionChange = { position : Int ->
        sliderPosition = position
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "0",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            DemoSlider(
                sliderPosition = sliderPosition,
                onPositionChange = handlePositionChange,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "25",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = "$sliderPosition %"
        )
    }
}



@Preview(showSystemUi = true)
@Composable
fun DemoTextPreview() {
    TipCalculatorTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DemoScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}