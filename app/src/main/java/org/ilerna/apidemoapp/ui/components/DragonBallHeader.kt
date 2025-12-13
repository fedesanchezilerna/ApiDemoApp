package org.ilerna.apidemoapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.ilerna.apidemoapp.R

/**
 * DragonBallHeader - Fixed header with Dragon Ball logo
 */
@Composable
fun DragonBallHeader(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Dragon Ball logo
        Image(
            painter = painterResource(id = R.drawable.dragon_ball_header),
            contentDescription = "Dragon Ball logo",
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 8.dp)
        )
    }

    HorizontalDivider(
        thickness = 4.dp,
        color = colors.primary
    )
}
