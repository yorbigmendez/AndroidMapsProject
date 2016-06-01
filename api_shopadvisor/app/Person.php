<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Person extends Model
{
    protected $fillable = array('id', 'fullName', 'email','pass');
    protected $hidden = array('created_at', 'updated_at');
}
