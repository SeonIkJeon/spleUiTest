package kr.co.toothlife.spleuitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.databinding.DataBindingUtil
import kr.co.toothlife.spleuitest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val TAG = javaClass.simpleName

    private var downX = 0
    private var downY = 0
    private var onSwipeReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

//        binding.motionLayout.transitionToEnd()
//        binding.motionLayout.transition
        binding.tvTest.setOnClickListener {
            Toast.makeText(this, "asdasdasdasdas", Toast.LENGTH_SHORT).show()
        }

        Log.d(TAG, "onCreate: ::")
//
//        binding.topImageContainer.setOnTouchListener { _, event ->
//            when (event.actionMasked) {
//                MotionEvent.ACTION_DOWN -> {
//                    println("ACTION DOWN")
//                    false
//                }
//                MotionEvent.ACTION_UP -> {
//                    println("ACTION UP")
//                    false
//                }
//                else -> false
//            }
//        }
//
//
//        binding.motionLayout.getTransition(R.id.ttll)
//            .setOnSwipe(null) // 처음 Null을 주고 체크한다.



        binding.motionLayout.isInteractionEnabled = true


        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                Log.d(TAG, "onTransitionStarted: ")
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                Log.d(TAG, "onTransitionChange: ")
//                if(startId == R.id.start) {
//                    Log.d(TAG, "onTransitionChange::::: "+progress)
//                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                Log.d(TAG, "onTransitionCompleted: " + currentId.toString())
                motionLayout?.let {
                    val isEndState = currentId == it.endState
                    Log.d(TAG, "onTransitionCompleted - isEndState : $isEndState")
//                    motionLayout.getTransition(R.id.end)
//                        .setOnSwipe(null) // 처음 Null을 주고 체크한다.
                        //여기서 동작 시켜보자
                        //Logger.d("Null인가?? : ${motionLayout.getTransition(R.id.transition_down).touchResponse}")
                    if (!isEndState) {
                        binding.motionLayout.setOnTouchListener { _, motionEvent ->
                            when (motionEvent.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    downX = motionEvent.rawX.toInt()
                                    downY = motionEvent.rawY.toInt()
                                    Log.d(TAG, "다운 되었다!! - downY : $downY")
                                }
                                MotionEvent.ACTION_MOVE -> {
                                    //Logger.d("downX : $downX / downY : $downY / moveX : ${motionEvent.x.toInt()} / moveY : ${motionEvent.y.toInt()}")
                                    val move = motionEvent.rawY - downY
//                                    Log.d(TAG, "무브중이다!! " + move)
                                        if (move > 30) {
                                            Log.d(
                                                TAG,
                                                "스와이프 생성한다!! - rawY : ${motionEvent.rawY.toInt() - downY}"
                                            )
                                            onSwipeReady = true
                                            binding.motionLayout.getTransition(R.id.ttll)
                                                .setOnSwipe(createOnSwipe(true))
                                            binding.motionLayout.setOnTouchListener(null)

                                            downX = 0
                                            downY = 0
                                        }
//                                        if(move < 0) {
//                                            Log.d(TAG, "위로 올린다~~~: "+move)
//                                            onSwipeReady = true
//                                            binding.motionLayout.getTransition(R.id.ttll)
//                                                .setOnSwipe(createOnSwipe(true))
//                                            binding.motionLayout.setOnTouchListener(null)
//
//                                            downX = 0
//                                            downY = 0
//                                        }

                                }
                                MotionEvent.ACTION_UP -> {
                                    downX = 0
                                    downY = 0
                                    Log.d(TAG, "업되었다!!")
                                    onSwipeReady = false
                                }
                            }
                            return@setOnTouchListener true
                        }
                    } else {
                        binding.motionLayout.setOnTouchListener { _, motionEvent ->
                            when (motionEvent.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    downX = motionEvent.rawX.toInt()
                                    downY = motionEvent.rawY.toInt()
                                    Log.d(TAG, "스몰 모드 다운 되었다!! - downY : $downY")
                                }
                                MotionEvent.ACTION_MOVE -> {
                                    //Logger.d("downX : $downX / downY : $downY / moveX : ${motionEvent.x.toInt()} / moveY : ${motionEvent.y.toInt()}")
                                    val move = downY - motionEvent.rawY
//                                    Log.d(TAG, "무브중이다!! " + move)
                                    Log.d(TAG, "스몰 모드 무브 되었다!! - move : ${motionEvent.rawY}")
//
                                    Log.d(TAG, "스몰 모드 무브 결과값! - move : ${move}")
                                    if (move > 6 && move < 30) {
                                        onSwipeReady = true
                                        binding.motionLayout.getTransition(R.id.ttll)
                                            .setOnSwipe(createOnSwipe(true))
                                        binding.motionLayout.setOnTouchListener(null)

                                        downX = 0
                                        downY = 0
                                    }
//                                        if(move < 0) {
//                                            Log.d(TAG, "위로 올린다~~~: "+move)
//                                            onSwipeReady = true
//                                            binding.motionLayout.getTransition(R.id.ttll)
//                                                .setOnSwipe(createOnSwipe(true))
//                                            binding.motionLayout.setOnTouchListener(null)
//
//                                            downX = 0
//                                            downY = 0
//                                        }

                                }
                                MotionEvent.ACTION_UP -> {
                                    if (downY - motionEvent.rawY.toInt() < 5) {
                                        Log.d(TAG, "스몰 모드 업인데 거의 클릭이다. "+(motionEvent.rawY.toInt() - downY))
                                        motionLayout.jumpToState(R.id.start)
                                        binding.motionLayout.setOnTouchListener(null)
                                    }
                                    downX = 0
                                    downY = 0
                                    Log.d(TAG, "스몰 모드 업되었다!!")
                                    onSwipeReady = false
                                }
                            }
                            return@setOnTouchListener true
                        }
                    }





                }
//                if(currentId == R.id.end){
//                    Log.d(TAG, "onTransitionCompleted:11111 ")
//                    binding.topImageContainer.setOnClickListener {
//                        binding.motionLayout.transitionToStart()
//                    }
//                }else {
////                    binding.motionLayout.setTransition(R.id.ttll)
//                    binding.motionLayout.setTransition(R.id.start, R.id.end)
//                }
//                binding.motionLayout.getTransition(R.id.end).setOnt
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                Log.d(TAG, "onTransitionTrigger: ")
            }
        })
    }

    private fun createOnSwipe(isEndState: Boolean): OnSwipe {
        return OnSwipe().apply {
            dragDirection = OnSwipe.DRAG_DOWN
            touchAnchorId = R.id.top_image_container
            touchRegionId = R.id.top_image_container
            touchAnchorSide = OnSwipe.SIDE_BOTTOM
        }
    }
}