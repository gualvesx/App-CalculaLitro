package com.example.fuelcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                FuelCalculatorApp()
            }
        }
    }
}

@Composable
fun FuelCalculatorApp() {
    // Valores fixos de eficiência (km/litro)
    val ethanolEfficiency = 8f  // Exemplo: 8 km/litro para etanol
    val gasolineEfficiency = 10f // Exemplo: 10 km/litro para gasolina

    var ethanolPrice by remember { mutableStateOf("") }
    var gasolinePrice by remember { mutableStateOf("") }
    var tankCapacity by remember { mutableStateOf("") }

    var autonomyEthanol by remember { mutableStateOf(0f) }
    var autonomyGasoline by remember { mutableStateOf(0f) }
    var costPerKmEthanol by remember { mutableStateOf(0f) }
    var costPerKmGasoline by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculadora de Autonomia",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Seção de preços
        Text(
            text = "Preços por Litro",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        OutlinedTextField(
            value = ethanolPrice,
            onValueChange = { ethanolPrice = it },
            label = { Text("Preço do Etanol (R$)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gasolinePrice,
            onValueChange = { gasolinePrice = it },
            label = { Text("Preço da Gasolina (R$)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Capacidade do tanque
        OutlinedTextField(
            value = tankCapacity,
            onValueChange = { tankCapacity = it },
            label = { Text("Capacidade do Tanque (L)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                calculateAutonomy(
                    ethanolPrice,
                    gasolinePrice,
                    ethanolEfficiency,
                    gasolineEfficiency,
                    tankCapacity,
                    onResult = { autonomyE, autonomyG, costE, costG ->
                        autonomyEthanol = autonomyE
                        autonomyGasoline = autonomyG
                        costPerKmEthanol = costE
                        costPerKmGasoline = costG
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Autonomia")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resultados
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Eficiência Fixa:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Etanol: ${ethanolEfficiency} km/L",
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Gasolina: ${gasolineEfficiency} km/L",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Autonomia com Etanol: ${"%.1f".format(autonomyEthanol)} km",
                fontWeight = FontWeight.Bold,
                color = Color.Green,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Autonomia com Gasolina: ${"%.1f".format(autonomyGasoline)} km",
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Custo por km - Etanol: R$ %.2f".format(costPerKmEthanol),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Custo por km - Gasolina: R$ %.2f".format(costPerKmGasoline),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

fun calculateAutonomy(
    ethanolPriceStr: String,
    gasolinePriceStr: String,
    ethanolEfficiency: Float,
    gasolineEfficiency: Float,
    tankCapacityStr: String,
    onResult: (Float, Float, Float, Float) -> Unit
) {
    try {
        val ethanolPrice = ethanolPriceStr.toFloat()
        val gasolinePrice = gasolinePriceStr.toFloat()
        val tankCapacity = tankCapacityStr.toFloat()

        // Cálculo da autonomia
        val autonomyEthanol = ethanolEfficiency * tankCapacity
        val autonomyGasoline = gasolineEfficiency * tankCapacity

        // Cálculo do custo por km
        val costPerKmEthanol = ethanolPrice / ethanolEfficiency
        val costPerKmGasoline = gasolinePrice / gasolineEfficiency

        onResult(autonomyEthanol, autonomyGasoline, costPerKmEthanol, costPerKmGasoline)
    } catch (e: NumberFormatException) {
        onResult(0f, 0f, 0f, 0f)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFuelCalculator() {
    MaterialTheme {
        FuelCalculatorApp()
    }
}