package com.music.breezze.ui.screens

import com.music.breezze.ui.components.contextClick
import com.music.breezze.ui.AppUi
import com.music.breezze.sharedui.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.DrawableResource
import androidx.compose.foundation.Image
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.music.breezze.ui.components.longPressMenuClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import com.music.breezze.ui.components.ShelfRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.LibraryMusic
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.Waves
import androidx.compose.material.icons.rounded.LibraryMusic as LibraryMusicIcon
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.RecentActors
import androidx.compose.material.icons.rounded.Mix
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.rounded.Nightlight
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.LocalCafe
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import com.music.breezze.ui.icons.BreezzeIcons
import coil3.compose.AsyncImage
import com.music.breezze.data.model.CARD_ART_PX
import com.music.breezze.data.model.HEADER_ART_PX
import com.music.breezze.data.model.HomeShelf
import com.music.breezze.data.model.ROW_ART_PX
import com.music.breezze.data.model.ShelfItem
import com.music.breezze.data.model.Song
import com.music.breezze.data.model.UiState
import java.util.Locale
import com.music.breezze.data.model.artworkAt
import com.music.breezze.data.settings.LibraryViewType
import com.music.breezze.ui.components.HERO_CARD_RATIO
import com.music.breezze.ui.components.MessageState
import com.music.breezze.ui.components.PAGE_GUTTER
import com.music.breezze.ui.components.RowMoreButton
import com.music.breezze.ui.components.PlayingAccent
import com.music.breezze.ui.components.PullToRefresh
import com.music.breezze.ui.components.SHELF_CARD_WIDTH
import com.music.breezze.ui.components.SearchPlayingBars
import com.music.breezze.ui.components.SignInBanner
import com.music.breezze.ui.components.feedMoreSkeleton
import com.music.breezze.ui.components.feedSkeleton
import com.music.breezze.ui.components.recentlyPlayedSkeleton
import com.music.breezze.ui.components.heroCardWidth
import com.music.breezze.ui.components.thumbnailBorder
import com.music.breezze.ui.components.trackColumnWidth
import com.music.breezze.ui.player.MeshGradientBackground
import com.music.breezze.ui.player.MeshPalette
import androidx.compose.animation.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.ScrollableState
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import androidx.compose.runtime.rememberInfiniteTransition
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.LibraryMusic
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.Waves
import androidx.compose.material.icons.rounded.LibraryMusic as LibraryMusicIcon
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.RecentActors
import androidx.compose.material.icons.rounded.Mix
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.rounded.Nightlight
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.LocalCafe
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.derivedStateOf
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.ScrollableState
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

/** How far the Listen now heading and feed sit above the shared top inset. */
private val HOME_TITLE_LIFT = 28.dp

private const val RECENTS_TITLE = "Recents"
private const val RECENT_TRACKS_PER_COLUMN = 4

private fun List<ShelfItem>.playingItemMatcher(song: Song?): (ShelfItem) -> Boolean {
    song ?: return { false }
    if (any { it.videoId == song.videoId }) return { it.videoId == song.videoId }
    val byName = firstOrNull { it.videoId != null && it.title == song.title && it.subtitle == song.artist }
        ?: return { false }
    return { it == byName }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: UiState<List<HomeShelf>>,
    listState: LazyListState,
    onItemClick: (ShelfItem, String) -> Unit,
    onRetry: () -> Unit,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    pullState: PullToRefreshState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    title: String?,
    signedIn: Boolean = true,
    onSignIn: (() -> Unit)? = null,
    onItemLongPress: ((ShelfItem) -> Unit)? = null,
    onLoadMore: (() -> Unit)? = null,
    loadingMore: Boolean = false,
    recentlyPlayedLoading: Boolean = false,
    leadHero: Boolean = true,
    currentSong: Song? = null,
    isPlaying: Boolean = false,
    /** Personalized greeting name */
    userName: String? = null,
    /** User's recently played for hero */
    heroTrack: Song? = null,
    /** Made for You shelves */
    madeForYouShelves: List<HomeShelf> = emptyList(),
    /** New releases shelf */
    newReleasesShelf: HomeShelf? = null,
    /** Moods & genres shelves */
    moodsShelves: List<HomeShelf> = emptyList(),
    /** Jump back in shelf */
    jumpBackInShelf: HomeShelf? = null,
) {
    val recentsViewType by AppUi.host.homeRecentsViewType.collectAsStateWithLifecycle()
    
    // Parallax scroll for hero
    val scrollState = rememberLazyListState()
    val heroOffset by derivedStateOf { 
        min(scrollState.firstVisibleItemScrollOffset.toFloat() * 0.5f, 200f) 
    }
    val heroAlpha by derivedStateOf { 
        max(1f - scrollState.firstVisibleItemScrollOffset.toFloat() / 300f, 0f) 
    }

    PullToRefresh(
        refreshing = refreshing,
        onRefresh = onRefresh,
        state = pullState,
        modifier = modifier,
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = if (title != null) {
                PaddingValues(
                    top = (contentPadding.calculateTopPadding() - HOME_TITLE_LIFT).coerceAtLeast(0.dp),
                    bottom = contentPadding.calculateBottomPadding(),
                )
            } else {
                contentPadding
            },
        ) {
            // ===== HERO SECTION =====
            item {
                AppleMusicHeroSection(
                    userName = userName,
                    heroTrack = heroTrack,
                    currentSong = currentSong,
                    isPlaying = isPlaying,
                    parallaxOffset = heroOffset,
                    alpha = heroAlpha,
                )
            }

            // ===== QUICK ACCESS CARDS =====
            item {
                QuickAccessCards(
                    modifier = Modifier.padding(horizontal = PAGE_GUTTER, vertical = 8.dp),
                )
            }

            // ===== RECENTLY PLAYED =====
            if (!recentlyPlayedLoading) {
                item {
                    RecentlyPlayedSection(
                        shelf = state.data.firstOrNull { it.title.equals(RECENTS_TITLE, ignoreCase = true) },
                        onItemClick = onItemClick,
                        onItemLongPress = onItemLongPress,
                        currentSong = currentSong,
                        isPlaying = isPlaying,
                        viewType = recentsViewType,
                        onViewTypeToggle = { AppUi.host.setHomeRecentsViewType(
                            if (recentsViewType == LibraryViewType.LIST) LibraryViewType.GRID else LibraryViewType.LIST
                        )},
                        modifier = Modifier.padding(horizontal = PAGE_GUTTER, top = 16.dp),
                    )
                }
            } else {
                item {
                    recentlyPlayedSkeleton(listLayout = recentsViewType == LibraryViewType.LIST)
                        .padding(horizontal = PAGE_GUTTER, top = 16.dp)
                }
            }

            // ===== MADE FOR YOU =====
            if (madeForYouShelves.isNotEmpty()) {
                item {
                    MadeForYouSection(
                        shelves = madeForYouShelves,
                        onItemClick = onItemClick,
                        onItemLongPress = onItemLongPress,
                        currentSong = currentSong,
                        isPlaying = isPlaying,
                        modifier = Modifier.padding(horizontal = PAGE_GUTTER, top = 16.dp),
                    )
                }
            }

            // ===== NEW RELEASES =====
            newReleasesShelf?.let { shelf ->
                item {
                    SectionWithHeroCards(
                        shelf = shelf,
                        onItemClick = onItemClick,
                        onItemLongPress = onItemLongPress,
                        currentSong = currentSong,
                        isPlaying = isPlaying,
                        modifier = Modifier.padding(horizontal = PAGE_GUTTER, top = 16.dp),
                    )
                }
            }

            // ===== MOODS & GENRES =====
            if (moodsShelves.isNotEmpty()) {
                item {
                    MoodsGenresSection(
                        shelves = moodsShelves,
                        onItemClick = onItemClick,
                        onItemLongPress = onItemLongPress,
                        currentSong = currentSong,
                        isPlaying = isPlaying,
                        modifier = Modifier.padding(horizontal = PAGE_GUTTER, top = 16.dp),
                    )
                }
            }

            // ===== JUMP BACK IN =====
            jumpBackInShelf?.let { shelf ->
                item {
                    SectionWithHeroCards(
                        shelf = shelf,
                        onItemClick = onItemClick,
                        onItemLongPress = onItemLongPress,
                        currentSong = currentSong,
                        isPlaying = isPlaying,
                        modifier = Modifier.padding(horizontal = PAGE_GUTTER, top = 16.dp),
                    )
                }
            }

            // ===== REGULAR SHELVES (rest of feed) =====
            when (state) {
                is UiState.Loading -> {
                    if (recentlyPlayedLoading) {
                        feedSkeleton(firstIsHero = false)
                    } else {
                        feedSkeleton(firstIsHero = leadHero)
                    }
                }
                is UiState.Error -> item {
                    MessageState(state.message, actionLabel = stringResource(Res.string.retry), onAction = onRetry)
                }
                is UiState.Success -> {
                    itemsIndexedShelves(
                        shelves = state.data,
                        onItemClick = onItemClick,
                        onItemLongPress = onItemLongPress,
                        currentSong = currentSong,
                        isPlaying = isPlaying,
                        firstIsHero = false, // We already showed hero
                        recentsViewType = recentsViewType,
                        onRecentsViewTypeToggle = {
                            AppUi.host.setHomeRecentsViewType(
                                if (recentsViewType == LibraryViewType.LIST) LibraryViewType.GRID else LibraryViewType.LIST
                            )
                        },
                        skipFirstIf = { it.title.equals(RECENTS_TITLE, ignoreCase = true) },
                    )
                    if (loadingMore) feedMoreSkeleton()
                }
            }
        }
    }

    if (onLoadMore != null && state is UiState.Success) {
        val loadMore by rememberUpdatedState(onLoadMore)
        LaunchedEffect(listState, loadingMore) {
            snapshotFlow {
                val layout = listState.layoutInfo
                (layout.visibleItemsInfo.lastOrNull()?.index ?: -1) to layout.totalItemsCount
            }.collect { (lastVisible, total) ->
                if (!loadingMore && total > 0 && lastVisible >= total - 3) loadMore()
            }
        }
    }
}

// ===== BEAUTIFUL HERO SECTION =====

@Composable
fun AppleMusicHeroSection(
    userName: String?,
    heroTrack: Song?,
    currentSong: Song?,
    isPlaying: Boolean,
    parallaxOffset: Float,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    val nowPlaying = currentSong ?: heroTrack
    val greeting = remember { buildGreeting(userName) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_GUTTER, vertical = 16.dp)
            .graphicsLayer {
                translationY = parallaxOffset
                this.alpha = alpha
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            // Greeting
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.W800,
                        letterSpacing = (-0.5).sp,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (userName != null) {
                    Text(
                        text = "What's next, $userName?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            // Now Playing Hero Card
            nowPlaying?.let { track ->
                NowPlayingHeroCard(
                    track = track,
                    isPlaying = isPlaying && currentSong?.videoId == track.videoId,
                    onPlayClick = { /* Handle play */ },
                )
            }
        }
    }
}

@Composable
fun NowPlayingHeroCard(
    track: Song,
    isPlaying: Boolean,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val artworkUrl = track.thumbnailUrl?.let { artworkAt(it, HEADER_ART_PX) }
    val palette = MeshPalette.fromUrl(artworkUrl)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = palette.surface?.copy(alpha = 0.9f) 
                ?? MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            // Background artwork with blur
            artworkUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .alpha(0.15f),
                )
            }
            
            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.6f to palette.surface?.copy(alpha = 0.3f) ?? MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.3f),
                            1f to palette.surface ?? MaterialTheme.colorScheme.surfaceContainerHigh,
                        )
                    )
            )
            
            // Content
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Album Art
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shadow(8.dp, RoundedCornerShape(12.dp)),
                ) {
                    artworkUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                        )
                    }
                }
                
                // Track Info
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.W800,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = track.artist,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (track.albumName != null) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = track.albumName!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    
                    // Play Button
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        IconButton(
                            onClick = { /* Play */ },
                            modifier = Modifier.size(56.dp),
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp),
                            )
                        }
                        
                        IconButton(
                            onClick = { /* Shuffle */ },
                            modifier = Modifier.size(48.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Shuffle,
                                contentDescription = "Shuffle",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

// ===== QUICK ACCESS CARDS =====

@Composable
fun QuickAccessCards(
    modifier: Modifier = Modifier,
) {
    val cards = listOf(
        QuickAccessCard("Library", "Your music collection", Icons.Rounded.LibraryMusic, MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer),
        QuickAccessCard("Downloads", "Available offline", Icons.Rounded.Download, MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer),
        QuickAccessCard("Recently Added", "New to your library", Icons.Rounded.RecentActors, MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer),
        QuickAccessCard("Playlists", "Your created playlists", Icons.Rounded.Folder, Color(0xFF00D4FF).copy(alpha = 0.2f), Color(0xFF00D4FF)),
    )
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        cards.forEach { card ->
            QuickAccessCardItem(
                title = card.title,
                subtitle = card.subtitle,
                icon = card.icon,
                containerColor = card.containerColor,
                contentColor = card.contentColor,
                modifier = Modifier.width(156.dp),
            )
        }
    }
}

data class QuickAccessCard(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val containerColor: Color,
    val contentColor: Color,
)

@Composable
fun QuickAccessCardItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(28.dp),
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W700),
                    color = contentColor.copy(alpha = 0.9f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

// ===== RECENTLY PLAYED SECTION =====

@Composable
fun RecentlyPlayedSection(
    shelf: HomeShelf?,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
    viewType: LibraryViewType,
    onViewTypeToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    shelf?.let { shelf ->
        Column(modifier = modifier.padding(bottom = 26.dp)) {
            SectionHeader(
                title = shelf.title,
                subtitle = shelf.subtitle,
                onMoreClick = { /* Show all */ },
            )
            
            val isCurrentItem = shelf.items.playingItemMatcher(currentSong)
            
            if (viewType == LibraryViewType.LIST) {
                BoxWithConstraints {
                    val columnWidth = trackColumnWidth(maxWidth)
                    ShelfRow(
                        contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(shelf.items.chunked(RECENT_TRACKS_PER_COLUMN)) { column ->
                            Column(Modifier.width(columnWidth)) {
                                column.forEach { item ->
                                    RecentTrackRow(
                                        item = item,
                                        onClick = { onItemClick(item) },
                                        onLongPress = onItemLongPress?.let { { it(item) } },
                                        isCurrent = isCurrentItem(item),
                                        isPlaying = isCurrentItem(item) && isPlaying,
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                BoxWithConstraints {
                    val cardWidth = heroCardWidth(maxWidth)
                    ShelfRow(
                        contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        items(shelf.items) { item ->
                            HeroCard(
                                item = item,
                                onClick = { onItemClick(item) },
                                onLongPress = onItemLongPress?.let { { it(item) } },
                                modifier = Modifier.width(cardWidth),
                                isCurrent = isCurrentItem(item),
                                isPlaying = isCurrentItem(item) && isPlaying,
                            )
                        }
                    }
                }
            }
        }
    }
}

// ===== MADE FOR YOU SECTION =====

@Composable
fun MadeForYouSection(
    shelves: List<HomeShelf>,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(bottom = 26.dp)) {
        SectionHeader(
            title = "Made for You",
            subtitle = "Updated weekly based on your listening",
            onMoreClick = { /* Show all */ },
        )
        
        ShelfRow(
            contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            items(shelves) { shelf ->
                val cardWidth = heroCardWidth(300.dp)
                HeroCard(
                    item = ShelfItem(
                        title = shelf.title,
                        subtitle = shelf.subtitle,
                        videoId = shelf.items.firstOrNull()?.videoId,
                        thumbnailUrl = shelf.items.firstOrNull()?.thumbnailUrl,
                        durationText = shelf.items.firstOrNull()?.durationText,
                    ),
                    onClick = { /* Navigate to shelf */ },
                    modifier = Modifier.width(cardWidth),
                    isCurrent = false,
                    isPlaying = false,
                    customTitle = shelf.title,
                    customSubtitle = shelf.subtitle,
                    customArtUrl = shelf.items.firstOrNull()?.thumbnailUrl,
                )
            }
        }
    }
}

// ===== SECTION WITH HERO CARDS (New Releases, Jump Back In) =====

@Composable
fun SectionWithHeroCards(
    shelf: HomeShelf,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(bottom = 26.dp)) {
        SectionHeader(
            title = shelf.title,
            subtitle = shelf.subtitle,
            onMoreClick = { /* Show all */ },
        )
        
        val isCurrentItem = shelf.items.playingItemMatcher(currentSong)
        val cardWidth = heroCardWidth(280.dp)
        
        ShelfRow(
            contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            items(shelf.items) { item ->
                HeroCard(
                    item = item,
                    onClick = { onItemClick(item) },
                    onLongPress = onItemLongPress?.let { { it(item) } },
                    modifier = Modifier.width(cardWidth),
                    isCurrent = isCurrentItem(item),
                    isPlaying = isCurrentItem(item) && isPlaying,
                )
            }
        }
    }
}

// ===== MOODS & GENRES SECTION =====

@Composable
fun MoodsGenresSection(
    shelves: List<HomeShelf>,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(bottom = 26.dp)) {
        SectionHeader(
            title = "Moods & Genres",
            subtitle = "Browse by how you feel",
            onMoreClick = { /* Show all */ },
        )
        
        ShelfRow(
            contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            items(shelves) { shelf ->
                val cardWidth = heroCardWidth(200.dp)
                MoodGenreCard(
                    shelf = shelf,
                    onClick = { /* Navigate to mood/genre */ },
                    modifier = Modifier.width(cardWidth),
                )
            }
        }
    }
}

@Composable
fun MoodGenreCard(
    shelf: HomeShelf,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val artworkUrl = shelf.items.firstOrNull()?.thumbnailUrl?.let { artworkAt(it, CARD_ART_PX) }
    
    Card(
        modifier = modifier
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.BottomStart,
        ) {
            artworkUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .alpha(0.85f),
                )
            }
            
            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.4f to MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.3f),
                            1f to MaterialTheme.colorScheme.surfaceContainerHigh,
                        )
                    )
            )
            
            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = shelf.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W700),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (shelf.subtitle.isNotBlank()) {
                    Text(
                        text = shelf.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

// ===== REUSABLE COMPONENTS =====

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    onMoreClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_GUTTER, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W800),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            subtitle?.let { sub ->
                Text(
                    text = sub,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        onMoreClick?.let { click ->
            IconButton(onClick = click) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Show all",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun HomeTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.displayLarge.copy(
            fontWeight = FontWeight.W800,
            letterSpacing = (-1.0).sp,
        ),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PAGE_GUTTER, top = 8.dp, bottom = 4.dp),
    )
}

private fun buildGreeting(name: String?): String {
    val hour = java.time.LocalTime.now().hour
    val timeGreeting = when {
        hour < 5 -> "Good night"
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        else -> "Good evening"
    }
    return if (name != null && name.isNotBlank()) {
        "$timeGreeting, $name"
    } else {
        timeGreeting
    }
}

// ===== SHELF ITEMS (existing) =====

private fun androidx.compose.foundation.lazy.LazyListScope.itemsIndexedShelves(
    shelves: List<HomeShelf>,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
    firstIsHero: Boolean = true,
    recentsViewType: LibraryViewType,
    onRecentsViewTypeToggle: () -> Unit,
    skipFirstIf: (HomeShelf) -> Boolean = { false },
) {
    var heroUsed = false
    shelves.forEachIndexed { index, shelf ->
        if (skipFirstIf(shelf)) return@forEachIndexed
        
        item(key = shelf.title + index) {
            val openItem: (ShelfItem) -> Unit = { item -> onItemClick(item, shelf.title) }
            
            if (!heroUsed && firstIsHero && index == 0 && shelf.title != RECENTS_TITLE) {
                HeroShelf(
                    shelf = shelf,
                    onItemClick = openItem,
                    onItemLongPress = onItemLongPress,
                    currentSong = currentSong,
                    isPlaying = isPlaying,
                )
                heroUsed = true
            } else if (index == 0 && shelf.title.equals(RECENTS_TITLE, ignoreCase = true)) {
                RecentShelf(
                    shelf = shelf,
                    onItemClick = openItem,
                    onItemLongPress = onItemLongPress,
                    currentSong = currentSong,
                    isPlaying = isPlaying,
                    viewType = recentsViewType,
                    onViewTypeToggle = onRecentsViewTypeToggle,
                )
            } else {
                Shelf(
                    shelf = shelf,
                    onItemClick = openItem,
                    onItemLongPress = onItemLongPress,
                    currentSong = currentSong,
                    isPlaying = isPlaying,
                )
            }
        }
    }
}

// ===== ORIGINAL COMPONENTS (from original HomeScreen.kt) =====

/** The same four-rows-per-page treatment used by an artist's Top songs. */
@Composable
private fun RecentShelf(
    shelf: HomeShelf,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
    viewType: LibraryViewType,
    onViewTypeToggle: () -> Unit,
) {
    Column(Modifier.padding(bottom = 26.dp)) {
        RecentSectionHeader(
            title = shelf.title,
            subtitle = shelf.subtitle,
            viewType = viewType,
            onViewTypeToggle = onViewTypeToggle,
        )
        val isCurrentItem = shelf.items.playingItemMatcher(currentSong)
        if (viewType == LibraryViewType.LIST) {
            BoxWithConstraints {
                val columnWidth = trackColumnWidth(maxWidth)
                ShelfRow(
                    contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(shelf.items.chunked(RECENT_TRACKS_PER_COLUMN)) { column ->
                        Column(Modifier.width(columnWidth)) {
                            column.forEach { item ->
                                RecentTrackRow(
                                    item = item,
                                    onClick = { onItemClick(item) },
                                    onLongPress = onItemLongPress?.let { { it(item) } },
                                    isCurrent = isCurrentItem(item),
                                    isPlaying = isCurrentItem(item) && isPlaying,
                                )
                            }
                        }
                    }
                }
            }
        } else {
            BoxWithConstraints {
                val cardWidth = heroCardWidth(maxWidth)
                ShelfRow(
                    contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    items(shelf.items) { item ->
                        HeroCard(
                            item = item,
                            onClick = { onItemClick(item) },
                            onLongPress = onItemLongPress?.let { { it(item) } },
                            modifier = Modifier.width(cardWidth),
                            isCurrent = isCurrentItem(item),
                            isPlaying = isCurrentItem(item) && isPlaying,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentSectionHeader(
    title: String,
    subtitle: String,
    viewType: LibraryViewType,
    onViewTypeToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = PAGE_GUTTER, vertical = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (subtitle.isNotBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable(onClick = onViewTypeToggle),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (viewType == LibraryViewType.LIST) {
                    BreezzeIcons.GridView
                } else {
                    BreezzeIcons.ListView
                },
                contentDescription = stringResource(
                    if (viewType == LibraryViewType.LIST) Res.string.grid_view else Res.string.list_view,
                ),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun HeroShelf(
    shelf: HomeShelf,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
) {
    Column(Modifier.padding(bottom = 26.dp)) {
        val isCurrentItem = shelf.items.playingItemMatcher(currentSong)
        BoxWithConstraints {
            val cardWidth = heroCardWidth(maxWidth)
            ShelfRow(
                contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                items(shelf.items) { item ->
                    HeroCard(
                        item = item,
                        onClick = { onItemClick(item) },
                        onLongPress = onItemLongPress?.let { { it(item) } },
                        modifier = Modifier.width(cardWidth),
                        isCurrent = isCurrentItem(item),
                        isPlaying = isCurrentItem(item) && isPlaying,
                    )
                }
            }
        }
    }
}

@Composable
private fun Shelf(
    shelf: HomeShelf,
    onItemClick: (ShelfItem) -> Unit,
    onItemLongPress: ((ShelfItem) -> Unit)?,
    currentSong: Song?,
    isPlaying: Boolean,
) {
    Column(Modifier.padding(bottom = 26.dp)) {
        Row(
            modifier = Modifier
                .padding(horizontal = PAGE_GUTTER, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = shelf.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (shelf.subtitle.isNotBlank()) {
                    Text(
                        text = shelf.subtitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        val isCurrentItem = shelf.items.playingItemMatcher(currentSong)
        BoxWithConstraints {
            val cardWidth = heroCardWidth(maxWidth)
            ShelfRow(
                contentPadding = PaddingValues(horizontal = PAGE_GUTTER),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                items(shelf.items) { item ->
                    HeroCard(
                        item = item,
                        onClick = { onItemClick(item) },
                        onLongPress = onItemLongPress?.let { { it(item) } },
                        modifier = Modifier.width(cardWidth),
                        isCurrent = isCurrentItem(item),
                        isPlaying = isCurrentItem(item) && isPlaying,
                    )
                }
            }
        }
    }
}

@Composable
fun HeroCard(
    item: ShelfItem,
    onClick: () -> Unit,
    onLongPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    isCurrent: Boolean = false,
    isPlaying: Boolean = false,
    customTitle: String? = null,
    customSubtitle: String? = null,
    customArtUrl: String? = null,
) {
    val artworkUrl = customArtUrl ?? item.thumbnailUrl?.let { artworkAt(it, CARD_ART_PX) }
    val title = customTitle ?? item.title
    val subtitle = customSubtitle ?? item.subtitle
    
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress,
            ),
        contentAlignment = Alignment.BottomStart,
    ) {
        artworkUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
            )
        }
        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.6f to MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                        1f to MaterialTheme.colorScheme.surface,
                    )
                ),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.W700),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (subtitle.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (isCurrent) {
                Spacer(Modifier.height(4.dp))
                PlayingAccent(isPlaying)
            }
        }
    }
}

@Composable
fun RecentTrackRow(
    item: ShelfItem,
    onClick: () -> Unit,
    onLongPress: (() -> Unit)? = null,
    isCurrent: Boolean = false,
    isPlaying: Boolean = false,
) {
    val artworkUrl = item.thumbnailUrl?.let { artworkAt(it, ROW_ART_PX) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress,
            )
            .background(
                if (isCurrent) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center,
        ) {
            artworkUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(4.dp)),
                )
            } ?: Icon(
                imageVector = Icons.Rounded.MusicNote,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp),
            )
            if (isCurrent) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    if (isPlaying) {
                        Icon(
                            imageVector = Icons.Rounded.Pause,
                            contentDescription = "Playing",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp),
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = "Paused",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
            }
        }
        Column(Modifier.weight(1f).padding(end = 8.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isCurrent) FontWeight.W700 else FontWeight.Normal,
                ),
                color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (item.subtitle.isNotBlank()) {
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isCurrent) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (isCurrent && isPlaying) {
            Icon(
                imageVector = Icons.Rounded.Waves,
                contentDescription = "Playing",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp).padding(end = 4.dp),
            )
        }
    }
}

@Composable
private fun PlayingAccent(isPlaying: Boolean) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        repeat(3) { i ->
            AnimatedBar(isPlaying, delay = i * 100L)
        }
    }
}

@Composable
private fun AnimatedBar(isPlaying: Boolean, delay: Long) {
    val infiniteTransition = rememberInfiniteTransition(label = "playing-bar")
    val height by infiniteTransition.animateFloat(
        initialValue = 4f,
        targetValue = if (isPlaying) (12..20).random().toFloat() else 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (300..500).random(),
                delayMillis = delay,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "bar-height",
    )
    Box(
        modifier = Modifier
            .width(2.dp)
            .height(height.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clip(RoundedCornerShape(1.dp)),
    )
}