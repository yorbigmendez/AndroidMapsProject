<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Requests;
use App\Person;

class PersonController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function getAll() {
        return Person::all();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  Request  $request
     * @return Response
     */
    public function store(Request $request) {
        $person = new Person;

        $person->id = $request->input('id');
        $person->fullName = $request->input('fullName');
        $person->email = $request->input('email');
        $person->pass = $request->input('pass');

        $person->save();

        return 'true';
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  Request  $id
     * @return Response
     */
    public function destroy($id) {
        $person = Person::find($id);

        $person->delete();

        return 'true';
    }
}
