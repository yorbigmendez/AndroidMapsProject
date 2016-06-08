<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\Friend;

class FriendController extends Controller
{
    public function getAll() {
        return Friend::all();
    }

    public function store(Request $request) {
        $friend = new Friend;

        $friend->id = $request->input('id');
        $friend->me_id = $request->input('me_id');
        $friend->friend_id = $request->input('friend_id');

        $friend->save();

        return 'true';
    }

    public function destroy($id) {
        $friend = Friend::find($id);

        $friend->delete();

        return 'true';
    }
}
