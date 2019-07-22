package com.longshihan.mvpcomponent.utils

import android.support.v7.widget.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.View

/**
 * Created by LONGHE001.
 * @time 2019/7/22 0022
 * @des
 * @function
 */
class GallerySnapHelper :SnapHelper(){
    private val INVALID_DISTANCE = 1f
    private val MILLISECONDS_PER_INCH = 40f
    private var mHorizontalHelper: OrientationHelper? = null
    private var mRecyclerView: RecyclerView? = null
    private val TAG = "GallerySnapHelper"
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        mRecyclerView=recyclerView
        super.attachToRecyclerView(recyclerView)
    }
    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(layoutManager, targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }
        return out
    }

    private fun distanceToStart(layoutManager: RecyclerView.LayoutManager, targetView: View, helper: OrientationHelper): Int {
        //找到targetView的中心坐标
        val childCenter = helper.getDecoratedStart(targetView) + helper.getDecoratedMeasurement(targetView) / 2
        val containerCenter: Int
        //找到容器（RecyclerView）的中心坐标
        if (layoutManager.clipToPadding) {
            containerCenter = helper.startAfterPadding + helper.totalSpace / 2
        } else {
            containerCenter = helper.end / 2
        }
        //两个中心坐标的差值就是targetView需要滚动的距离
        return childCenter - containerCenter

        //    return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    override fun createSnapScroller(layoutManager: RecyclerView.LayoutManager?): LinearSmoothScroller? {
        return if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            null
        } else object : LinearSmoothScroller(mRecyclerView!!.context) {
            override fun onTargetFound(targetView: View, state: RecyclerView.State?, action: Action) {
                val snapDistances = calculateDistanceToFinalSnap(mRecyclerView!!.layoutManager, targetView)
                val dx = snapDistances!![0]
                val dy = snapDistances[1]
                val time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)))
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator)
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }
    }

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val currentPosition = layoutManager.getPosition(currentView)
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val vectorProvider = layoutManager as RecyclerView.SmoothScroller.ScrollVectorProvider
        // deltaJumps sign comes from the velocity which may not match the order of children in
        // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
        // get the direction.
        val vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1)
                ?: // cannot get a vector for the given position.
                return RecyclerView.NO_POSITION

        //在松手之后,列表最多只能滚多一屏的item数
        val deltaThreshold = layoutManager.width / getHorizontalHelper(layoutManager).getDecoratedMeasurement(currentView)

        var hDeltaJump: Int
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getHorizontalHelper(layoutManager), velocityX, 0
            )

            if (hDeltaJump > deltaThreshold) {
                hDeltaJump = deltaThreshold
            }
            if (hDeltaJump < -deltaThreshold) {
                hDeltaJump = -deltaThreshold
            }

            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump
            }
        } else {
            hDeltaJump = 0
        }

        if (hDeltaJump == 0) {
            return RecyclerView.NO_POSITION
        }

        var targetPos = currentPosition + hDeltaJump
        if (targetPos < 0) {
            targetPos = 0
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1
        }
        return targetPos
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return findCenterView(layoutManager, getHorizontalHelper(layoutManager))
    }

    private fun findCenterView(layoutManager: RecyclerView.LayoutManager,
                               helper: OrientationHelper): View? {
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return null
        }
        val firstChildPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val centerPosition = (firstChildPosition + layoutManager.findLastVisibleItemPosition()) / 2
        Log.d(TAG, centerPosition.toString() + "")
        var closestChild: View? = null
        //找到RecyclerView的中心坐标
        val center: Int
        if (layoutManager.getClipToPadding()) {
            center = helper.startAfterPadding + helper.totalSpace / 2
        } else {
            center = helper.end / 2
        }
        var absClosest = Integer.MAX_VALUE

        //遍历当前layoutManager中所有的ItemView
        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            //ItemView的中心坐标
            val childCenter = helper.getDecoratedStart(child) + helper.getDecoratedMeasurement(child) / 2
            //计算此ItemView与RecyclerView中心坐标的距离
            val absDistance = Math.abs(childCenter - center)

            //对比每个ItemView距离到RecyclerView中心点的距离，找到那个最靠近中心的ItemView然后返回
            if (absDistance < absClosest) {
                absClosest = absDistance
                closestChild = child
            }
        }
        return closestChild
    }

    private fun findStartView(layoutManager: RecyclerView.LayoutManager, helper: OrientationHelper): View? {
        if (layoutManager is LinearLayoutManager) {
            val firstChildPosition = layoutManager.findFirstVisibleItemPosition()
            val centerPosition = (firstChildPosition + layoutManager.findLastVisibleItemPosition()) / 2

            if (firstChildPosition == RecyclerView.NO_POSITION) {
                return null
            }
            Log.d(TAG, centerPosition.toString() + ":" + mRecyclerView!!.adapter.getItemId(centerPosition))
            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                return null
            }

            val firstChildView = layoutManager.findViewByPosition(centerPosition)
            return if (helper.getDecoratedEnd(firstChildView) >= helper.getDecoratedMeasurement(firstChildView) / 2 && helper.getDecoratedEnd(firstChildView) > 0) {
                firstChildView
            } else {
                layoutManager.findViewByPosition(centerPosition + 1)
            }
        } else {
            return null
        }
    }


    private fun estimateNextPositionDiffForFling(layoutManager: RecyclerView.LayoutManager,
                                                 helper: OrientationHelper, velocityX: Int, velocityY: Int): Int {
        val distances = calculateScrollDistance(velocityX, velocityY)
        val distancePerChild = computeDistancePerChild(layoutManager, helper)
        if (distancePerChild <= 0) {
            return 0
        }
        val distance = distances[0]
        return if (distance > 0) {
            Math.floor((distance / distancePerChild).toDouble()).toInt()
        } else {
            Math.ceil((distance / distancePerChild).toDouble()).toInt()
        }
    }

    private fun computeDistancePerChild(layoutManager: RecyclerView.LayoutManager,
                                        helper: OrientationHelper): Float {
        var minPosView: View? = null
        var maxPosView: View? = null
        var minPos = Integer.MAX_VALUE
        var maxPos = Integer.MIN_VALUE
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return INVALID_DISTANCE
        }

        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            val pos = layoutManager.getPosition(child)
            if (pos == RecyclerView.NO_POSITION) {
                continue
            }
            if (pos < minPos) {
                minPos = pos
                minPosView = child
            }
            if (pos > maxPos) {
                maxPos = pos
                maxPosView = child
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE
        }
        val start = Math.min(
                helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView)
        )
        val end = Math.max(
                helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView)
        )
        val distance = end - start
        return if (distance == 0) {
            INVALID_DISTANCE
        } else 1f * distance / (maxPos - minPos + 1)
    }


    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper!!
    }

    private var mOnCenterListener: OnCenterListener? = null

    fun setOnCenterListener(onCenterListener: OnCenterListener) {
        mOnCenterListener = onCenterListener
    }

    interface OnCenterListener {
        fun setOnCenterListener(position: Int)
    }

}