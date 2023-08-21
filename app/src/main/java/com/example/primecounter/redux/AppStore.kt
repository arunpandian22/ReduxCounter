package com.example.primecounter.redux


/**
 * Created by Arun Pandian  on 18/08/23.
 */
open class AppStore<State>(
    private val initialState: State,
    private val reducers: List<Reducer<State>>, private val middleware: List<Middleware<State>>,
) : Store<State> {
    private var currentState: State = initialState
    private val subscriptions = arrayListOf<Subscription<State>>()

    override fun getState() = currentState

    override fun dispatch(action: Action) {
        val newAction = applyMiddleware(currentState, action)
        val newState = applyReducers(currentState, newAction)
        if (newState == currentState) {
            // No change in state
            return
        }
        currentState = newState
        subscriptions.forEach { it(currentState, ::dispatch) }
    }

    override fun unsubscribe(subscription: Subscription<State>) {
        subscriptions.remove(subscription)
    }

    override fun subscribe(subscription: Subscription<State>): Unsubscribe {
        subscriptions.add(subscription)
        subscription(currentState, ::dispatch)
        return { subscriptions.remove(subscription) }
    }

    private fun applyReducers(current: State, action: Action): State {
        var newState = current
        for (reducer in reducers) {
            newState = reducer(newState, action)
        }
        return newState
    }

    private fun applyMiddleware(state: State, action: Action): Action {
        return next(0)(state, action, ::dispatch)
    }

    private fun next(index: Int): Next<State> {
        if (index == middleware.size) {
            // Last link of the chain. It just returns the action as is.
            return { _, action, _ -> action }
        }

        return { state, action, dispatch ->
            middleware[index].invoke(
                state,
                action,
                dispatch,
                next(index + 1)
            )
        }
    }
}