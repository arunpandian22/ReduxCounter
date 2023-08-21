package com.example.primecounter.redux

/**
 * Created by Arun Pandian  on 18/08/23.
 */

interface Action
typealias Reducer<State> = (State, Action) -> State
typealias Subscription<State> = (State, Dispatch) -> Unit
typealias Dispatch = (Action) -> Unit
typealias Unsubscribe = () -> Unit
typealias Next<State> = (State, Action, Dispatch) -> Action
typealias Middleware<State> = (State, Action, Dispatch, Next<State>) -> Action

interface Store<State> {
    fun getState(): State
    fun dispatch(action: Action)
    fun subscribe(subscription: Subscription<State>): Unsubscribe
    fun unsubscribe(subscription: Subscription<State>)
}

