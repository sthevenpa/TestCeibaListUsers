package com.test.testceibalistusers.data.repository

enum class Status {
    INIT,
    SUCCESS,
    FAILED,
    LOADING
}


data class StateApp<out T>(val status: Status, val data: T?=null, val message:String?="", val code: Int?=0) {

    companion object {

        fun <T> init(): StateApp<T> {
            return StateApp(Status.INIT)
        }

        // When the call is loading set the state
        // as Loading and rest as null
        fun <T> loading(message: String): StateApp<T> {
            return StateApp(Status.LOADING)
        }

        // In case of Success,set status as
        // Success and data as the response
        fun <T> success(data: T?): StateApp<T> {
            return StateApp(Status.SUCCESS, data)
        }

        // In case of failure ,set state to Error ,
        // add the error message,set data to null
        fun <T> failed(message: String, code:Int): StateApp<T> {
            return StateApp(Status.FAILED, message=message, code = code)
        }
    }


}
