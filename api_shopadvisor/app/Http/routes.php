<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});



Route::resource('place', 'PlaceController');
Route::post('place/add', 'PlaceController@store');
Route::resource('user', 'UserController');

//Person
Route::post('person/add', 'PersonController@store');
Route::get('person/all', 'PersonController@getAll');
Route::delete('person/delete/{id}', 'PersonController@destroy');

//Rout
Route::post('rout/add', 'RoutController@store');
Route::get('rout/all', 'RoutController@getAll');
Route::get('rout/get/{owner_id}', 'RoutController@getRoutByPerson');
Route::delete('rout/delete/{id}', 'RoutController@destroy');

//Friend
Route::post('friend/add', 'FriendController@store');
Route::get('friend/all', 'FriendController@getAll');
Route::delete('friend/delete/{id}', 'FriendController@destroy');

//Point
Route::post('point/add', 'PointController@store');
Route::get('point/all', 'PointController@getAll');
Route::delete('point/delete/{id}', 'PointController@destroy');