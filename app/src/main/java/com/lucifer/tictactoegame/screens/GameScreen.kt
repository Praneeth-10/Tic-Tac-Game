package com.lucifer.tictactoegame.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucifer.tictactoegame.models.BoardCellValue
import com.lucifer.tictactoegame.models.GameState
import com.lucifer.tictactoegame.models.UserAction
import com.lucifer.tictactoegame.models.VictoryType
import com.lucifer.tictactoegame.ui.theme.Aqua
import com.lucifer.tictactoegame.ui.theme.BlueCustom
import com.lucifer.tictactoegame.ui.theme.GrayBackground
import com.lucifer.tictactoegame.ui.theme.GreenishYellow
import com.lucifer.tictactoegame.viewModel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(GrayBackground)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(10.dp),
                        spotColor = Color.Gray,
                        clip = true
                    )
                    .background(Color(0x70FFC400), shape = RoundedCornerShape(10.dp))
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                ){

                Text(text = "Player 'O'", fontSize = 16.sp)
                Text( text = "${state.playerCircleCount}",
                    fontSize = 50.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    color = Aqua)
            }

            Text(text = "Draw: ${state.playerDrawCount}", fontSize = 16.sp,
                modifier = Modifier
                    .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = Color.Gray,
                    clip = true
                    )
                    .background(Color(0xFFDE9678), shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
            )

            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(10.dp),
                        spotColor = Color.Gray,
                        clip = true
                    )
                    .background(Color(0x70FFC400), shape = RoundedCornerShape(10.dp))
                    .padding(15.dp)){
                Text(text = "Player 'X'", fontSize = 16.sp)
                Text( text = "${state.playerCrossCount}",
                    fontSize = 50.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    color = GreenishYellow)
            }
        }

        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            color = BlueCustom
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            contentAlignment = Alignment.Center
        ) {
            BoardBase()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center
            ) {
                viewModel.boardItems.forEach { (cellNo, boardCellValue) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(15.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    viewModel.onAction(UserAction.BoardTapped(cellNo))
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = viewModel.boardItems[cellNo] != BoardCellValue.NONE,
                                enter = scaleIn(tween(400))
                            ) {
                                if (boardCellValue == BoardCellValue.CIRCLE){
                                    Circle()
                                }
                                else if (boardCellValue == BoardCellValue.CROSS){
                                    Cross()
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = state.hasWon,
                    enter = fadeIn(tween(1500))
                    ) {
                    DrawVictoryLine(state = state)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.hintText,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
            Button(
                onClick = { viewModel.onAction(UserAction.PlayAgainButtonClicked) },
                elevation = ButtonDefaults.buttonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueCustom,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 20.sp
                )
            }
        }


    }
}

@Composable
fun DrawVictoryLine(state : GameState) {
    when(state.victoryType){
        VictoryType.HORIZONTAL1 -> WinHorizontalLine1()
        VictoryType.HORIZONTAL2 -> WinHorizontalLine2()
        VictoryType.HORIZONTAL3 -> WinHorizontalLine3()
        VictoryType.VERTICAL1 -> WinVerticalLine1()
        VictoryType.VERTICAL2 -> WinVerticalLine2()
        VictoryType.VERTICAL3 -> WinVerticalLine3()
        VictoryType.DIAGONAL1 -> WinDiagonalLine1()
        VictoryType.DIAGONAL2 -> WinDiagonalLine2()
        else -> {}
    }
}

@Composable
fun ShimmerEffect() {
    val colors = listOf(Color.Transparent, Color.Green)
    val transition = rememberInfiniteTransition(label = "")
    val animatedProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        ), label = ""
    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .background(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(30f, 30f),
                end = Offset(40f, animatedProgress * 1000f)
            )
        )
    )
}


@Preview
@Composable
fun PreviewScr() {
    GameScreen(viewModel = GameViewModel())
}