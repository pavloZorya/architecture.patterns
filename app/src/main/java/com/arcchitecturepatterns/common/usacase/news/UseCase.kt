package com.arcchitecturepatterns.common.usacase.news

abstract class UseCase<From, To>(private val transform: Transformer<From, To>)