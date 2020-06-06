package com.app.covid.clickinterface

interface OnClickListener<in T> {
    fun onItemClick(clickedObject: T)
}