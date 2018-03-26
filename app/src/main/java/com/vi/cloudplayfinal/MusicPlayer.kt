package com.vi.cloudplayfinal

import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.mtechviral.mplaylib.MusicFinder
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class MusicPlayer : AppCompatActivity() {

    var albumArt: ImageView?=null
    var play: ImageButton?=null
    var shuffle: ImageButton?=null
    var songTitle: TextView?=null
    var songArtist: TextView?=null

    var mediaPlayer: MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        createPlayer()
    }

    private fun createPlayer(){
        var songsJob= async {
            val songFinder=MusicFinder(contentResolver)
            songFinder.prepare()
            songFinder.allSongs

        }

        launch(kotlinx.coroutines.experimental.android.UI) {
            val songs= songsJob.await()
            val playerUI= object : AnkoComponent<MusicPlayer> {
                override fun createView(ui: AnkoContext<MusicPlayer>)= with(ui) {

                    relativeLayout{
                        backgroundColor=Color.BLACK
                        albumArt=imageView{
                            scaleType=ImageView.ScaleType.FIT_CENTER
                        }.lparams(matchParent, matchParent)

                        verticalLayout {
                            backgroundColor=Color.parseColor("#99000000")
                            songTitle=textView {
                                textColor=Color.WHITE
                                typeface= Typeface.DEFAULT_BOLD
                                textSize=18f
                            }
                            songArtist=textView {
                                textColor=Color.WHITE
                            }

                            linearLayout {
                                play=imageButton {
                                    imageResource=R.drawable.ic_play_arrow_black_24dp
                                    onClick {
                                        playmusic()
                                    }

                                }.lparams(0, wrapContent,0.5f)

                                shuffle=imageButton {
                                    imageResource=R.drawable.ic_shuffle_black_24dp
                                    onClick {
                                        shufflem()
                                    }

                                }.lparams(0, wrapContent,0.5f)
                            }.lparams(matchParent, wrapContent){
                                topMargin=dip(5)
                            }



                        }.lparams(matchParent, wrapContent){
                            alignParentBottom()
                        }

                    }

                }
                fun shufflem(){
                    Collections.shuffle(songs)
                    val song=songs[0]
                    mediaPlayer?.reset()
                    mediaPlayer= MediaPlayer.create(ctx,song.uri)
                    mediaPlayer?.setOnCompletionListener {
                        shufflem()
                    }
                    albumArt?.imageURI=song.albumArt
                    songTitle?.text=song.title
                    songArtist?.text=song.artist
                    mediaPlayer?.start()
                    play?.imageResource=R.drawable.ic_pause_black_24dp
                }
                fun playmusic(){
                    var isSongplaying:Boolean?=mediaPlayer?.isPlaying
                    if (isSongplaying==true){
                        mediaPlayer?.pause()
                        play?.imageResource=R.drawable.ic_play_arrow_black_24dp

                    }
                    else{
                        mediaPlayer?.start()
                        play?.imageResource=R.drawable.ic_pause_black_24dp
                    }
                }
                
            }
            playerUI.setContentView(this@MusicPlayer)
            playerUI.shufflem()



        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

}
