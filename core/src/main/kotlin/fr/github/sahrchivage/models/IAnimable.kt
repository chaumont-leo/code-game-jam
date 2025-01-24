package fr.github.sahrchivage.models

import fr.github.sahrchivage.enums.AnimationEnum

interface IAnimable {
    fun startAnimate(animation: AnimationEnum)
}
