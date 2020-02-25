package com.arcchitecturepatterns.common.usacase.news

interface Transformer<From, To> {
    operator fun invoke(data: From): To
}