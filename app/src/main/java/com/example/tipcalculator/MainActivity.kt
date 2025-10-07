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
import kotlin.math.roundToInt

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

    // Рассчитываем итоговую сумму
    val amount = orderAmount.toDoubleOrNull() ?: 0.0
    val tipAmount = amount * tipPercent / 100
    val discountAmount = amount * discountPercent / 100
    val totalAmount = amount + tipAmount - discountAmount

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

        // Результаты расчета
        CalculationResults(
            tipAmount = tipAmount,
            discountAmount = discountAmount,
            totalAmount = totalAmount
        )
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

@Composable
fun CalculationResults(
    tipAmount: Double,
    discountAmount: Double,
    totalAmount: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Итого:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ResultRow(label = "Сумма чаевых:", value = "${tipAmount.roundToInt()} руб")
        ResultRow(label = "Сумма скидки:", value = "${discountAmount.roundToInt()} руб")
        ResultRow(label = "Итоговая сумма:", value = "${totalAmount.roundToInt()} руб")
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
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