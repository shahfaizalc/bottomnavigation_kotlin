package com.guiado.akbhar.views

import android.view.View
import androidx.annotation.AnimRes
import androidx.annotation.StyleRes
import androidx.fragment.app.FragmentTransaction

import java.util.ArrayList

/**
 *
 */


class FragNavTransactionOptions private constructor(builder: Builder) {
    internal var sharedElements: List<Pair<View, String>>? = null
    @FragNavController.Transit
    internal var transition = FragmentTransaction.TRANSIT_NONE
    @AnimRes
    internal var enterAnimation = 0
    @AnimRes
    internal var exitAnimation = 0
    @AnimRes
    internal var popEnterAnimation = 0
    @AnimRes
    internal var popExitAnimation = 0
    @StyleRes
    internal var transitionStyle = 0
    internal var breadCrumbTitle: String? = null
    internal var breadCrumbShortTitle: String? = null

    init {
        sharedElements = builder.sharedElements
        transition = builder.transition
        enterAnimation = builder.enterAnimation
        exitAnimation = builder.exitAnimation
        transitionStyle = builder.transitionStyle
        popEnterAnimation = builder.popEnterAnimation
        popExitAnimation = builder.popExitAnimation
        breadCrumbTitle = builder.breadCrumbTitle
        breadCrumbShortTitle = builder.breadCrumbShortTitle
    }

    class Builder() {
        internal var sharedElements: MutableList<Pair<View, String>>? = null
        internal var transition: Int = 0
        internal var enterAnimation: Int = 0
        internal var exitAnimation: Int = 0
        internal var transitionStyle: Int = 0
        internal var popEnterAnimation: Int = 0
        internal var popExitAnimation: Int = 0
        internal var breadCrumbTitle: String? = null
        internal var breadCrumbShortTitle: String? = null

        fun addSharedElement(`val`: Pair<View, String>): Builder {
            if (sharedElements == null) {
                sharedElements = ArrayList(3)
            }
            sharedElements!!.add(`val`)
            return this
        }

        fun sharedElements(`val`: MutableList<Pair<View, String>>): Builder {
            sharedElements = `val`
            return this
        }

        fun transition(@FragNavController.Transit `val`: Int): Builder {
            transition = `val`
            return this
        }

        fun customAnimations(@AnimRes enterAnimation: Int, @AnimRes exitAnimation: Int): Builder {
            this.enterAnimation = enterAnimation
            this.exitAnimation = exitAnimation
            return this
        }

        fun customAnimations(@AnimRes enterAnimation: Int, @AnimRes exitAnimation: Int, @AnimRes popEnterAnimation: Int, @AnimRes popExitAnimation: Int): Builder {
            this.popEnterAnimation = popEnterAnimation
            this.popExitAnimation = popExitAnimation
            return customAnimations(enterAnimation, exitAnimation)
        }


        fun transitionStyle(@StyleRes `val`: Int): Builder {
            transitionStyle = `val`
            return this
        }

        fun breadCrumbTitle(`val`: String): Builder {
            breadCrumbTitle = `val`
            return this
        }

        fun breadCrumbShortTitle(`val`: String): Builder {
            breadCrumbShortTitle = `val`
            return this
        }

        fun build(): FragNavTransactionOptions {
            return FragNavTransactionOptions(this)
        }
    }

    companion object {

        fun newBuilder(): Builder {
            return Builder()
        }
    }
}

