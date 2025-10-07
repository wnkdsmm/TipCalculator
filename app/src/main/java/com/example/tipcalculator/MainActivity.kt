package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var orderAmount by remember { mutableStateOf("") }
    var tipPercent by remember { mutableIntStateOf(0) }
    var dishCount by remember { mutableStateOf("") }

    // Рассчитываем скидку
    val discountPercent = when (val count = dishCount.toIntOrNull() ?: 0) {
        in 1..2 -> 3
        in 3..5 -> 5
        in 6..10 -> 7
        else -> if (count > 10) 10 else 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Калькулятор чаевых",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Поле ввода суммы заказа
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "Сумма заказа:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(120.dp)
            )
            TextField(
                value = orderAmount,
                onValueChange = { orderAmount = it }
            )
        }

        // Поле ввода количества блюд
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "Количество блюд:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(120.dp)
            )
            TextField(
                value = dishCount,
                onValueChange = { dishCount = it }
            )
        }

        // Слайдер чаевых
        Text(
            text = "Чаевые:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "0",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Slider(
                modifier = Modifier.weight(1f),
                valueRange = 0f..25f,
                value = tipPercent.toFloat(),
                onValueChange = { tipPercent = it.toInt() }
            )
            Text(
                text = "25",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = "$tipPercent %",
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Секция скидок
        DiscountSection(discountPercent)
    }
}

@Composable
fun DiscountSection(discountPercent: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Скидка:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Радиокнопки со скидками
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DiscountRadioButton(percent = 3, selected = discountPercent == 3)
            DiscountRadioButton(percent = 5, selected = discountPercent == 5)
            DiscountRadioButton(percent = 7, selected = discountPercent == 7)
            DiscountRadioButton(percent = 10, selected = discountPercent == 10)
        }
    }
}

@Composable
fun DiscountRadioButton(percent: Int, selected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = if (selected) "●" else "○",
            style = MaterialTheme.typography.displaySmall,
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "$percent%",
            style = MaterialTheme.typography.titleMedium,
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}