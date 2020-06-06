package com.app.covid.clickinterface

interface OnLongClickListener<in T> {
    fun onItemLongClick(clickedObject: T)
}