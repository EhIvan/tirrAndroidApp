package com.example.tirrapp.db

import android.provider.BaseColumns

object MyDbNameClass : BaseColumns {

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "tirrDB.db"

    //таблица для соревнований, подумать мб и для тренировок.
    const val TABLE_NAME = "events"
    const val COLUMN_NAME_NAME = "name_event"
    const val COLUMN_NAME_DISCIPLINE = "discipline"
    const val COLUMN_NAME_SCOPE = "scope"
    const val COLUMN_NAME_STATUS = "status"
    const val COLUMN_NAME_DATE = "date"
    const val COLUMN_NAME_PLACE = "place"
    const val COLUMN_NAME_INSTRUCTOR = "instructor"
    const val COLUMN_NAME_EVENT_TYPE = "event_type"

    const val CREATE_TABLE = "create table if not exists $TABLE_NAME ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_NAME TEXT, "+
            "$COLUMN_NAME_DISCIPLINE text, $COLUMN_NAME_SCOPE text, $COLUMN_NAME_STATUS text, "+
            "$COLUMN_NAME_DATE text, $COLUMN_NAME_PLACE text, $COLUMN_NAME_INSTRUCTOR text, " +
            "$COLUMN_NAME_EVENT_TYPE text)"


    //Таблица для участников
    const val TABLE_USERS = "users"
    const val TABLE_USERS_SURNAME = "surname"
    const val TABLE_USERS_NAME = "name"
    const val TABLE_USERS_CATEGORY = "category"
    //Создание таблицы users
    const val CREATE_TABLE_USERS = "create table if not exists $TABLE_USERS ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $TABLE_USERS_SURNAME TEXT, "+
            "$TABLE_USERS_NAME text, $TABLE_USERS_CATEGORY text)"


    //Таблица user_category
    const val TABLE_USER_CATEGORY = "user_category"
    const val TABLE_USER_CATEGORY_ID = "user_id"
    const val TABLE_USER_CATEGORY_CATEGORY = "category"
    const val TABLE_USER_CATEGORY_POWER_FACTOR = "power_factor"
    //Создание таблицы user_category
    const val CREATE_TABLE_USER_CATEGORY = "create table if not exists $TABLE_USER_CATEGORY ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $TABLE_USER_CATEGORY_ID INTEGER, "+
            "$TABLE_USER_CATEGORY_CATEGORY text, $TABLE_USER_CATEGORY_POWER_FACTOR text)"



    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"




}