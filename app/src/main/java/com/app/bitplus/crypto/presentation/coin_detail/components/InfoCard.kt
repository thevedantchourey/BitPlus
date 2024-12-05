package com.app.bitplus.crypto.presentation.coin_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bitplus.R
import com.app.bitplus.ui.theme.BitPlusTheme
import com.app.bitplus.ui.theme.onPrimaryContainerDark
import com.app.bitplus.ui.theme.onPrimaryContainerLight
import com.app.bitplus.ui.theme.onTertiaryDark
import com.app.bitplus.ui.theme.onTertiaryLight
import com.app.bitplus.ui.theme.primaryContainerDark
import com.app.bitplus.ui.theme.primaryContainerLight




@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    formattedText: String,
    icon: ImageVector,
    flowColor: Color?
) {

    val primaryContainerColor = if(isSystemInDarkTheme()){
        primaryContainerDark
    }else{
        primaryContainerLight
    }

    val tertiaryOnContainer = if(isSystemInDarkTheme()){
        onTertiaryLight
    }else{
        onTertiaryDark
    }


    val primaryOnContainer = if(isSystemInDarkTheme()){
        onPrimaryContainerDark
    }else{
        onPrimaryContainerLight
    }

    Card(
        shape = RectangleShape,
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(primaryContainerColor)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = icon,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                label = "iconAnimation"
            ) {icon->
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(16.dp),
                    tint = tertiaryOnContainer
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            AnimatedContent(
                targetState = formattedText,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                label = "valueAnimation"
            ) {formattedText->
                Text(
                    text = formattedText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = flowColor ?: primaryOnContainer
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = primaryOnContainer
            )
        }
    }

}


@PreviewLightDark
@Composable
private fun InfoCardPreview(){
    BitPlusTheme {
        InfoCard(title = "Price", formattedText = "$ 63,157.44", icon =ImageVector.vectorResource(id = (R.drawable.dollar)), flowColor = null)
    }
}