package com.sidav.gdxgame

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx

fun debugMsg(msg: String) {
    debugMsg("DEBUG", msg)
}

fun debugMsg(tag: String, msg: String) {
    if (Gdx.app.logLevel != Application.LOG_DEBUG)
        Gdx.app.logLevel = Application.LOG_DEBUG

    Gdx.app.debug(tag, msg)
}
