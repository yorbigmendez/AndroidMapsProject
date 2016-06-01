<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\User;
use Illuminate\Support\Facades\Input;

class UserController extends Controller
{
    public function index (){
      $User = User::all();
      return response()->json($User);
    }

    public function store() {
      $place = new User;
      $place->name = Input::get ('name');
      $place->latitude = Input::get ('email');
      $place->longitude = Input::get ('password');
      $place->save();
      return response()->json($place);
    }

    public function update($id) {
      $place = User::find($id);
      $place->name = Input::get ('name');
      $place->latitude = Input::get ('email');
      $place->longitude = Input::get ('password');
      $place->save();
      return response()->json($place);
    }

    public function destroy($id) {
      $place = User::find($id)->delete();
    }
}
