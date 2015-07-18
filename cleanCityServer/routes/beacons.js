var express = require('express');
var router = express.Router();

var mongoose = require('mongoose');
var Beacon = require('../models/Beacon.js');


/* GET all beacons. */
router.get('/', function(req, res, next) {
	Beacon.find(function (err, beacons) {
		if (err) { 
			return next (err); // pass on Error
		} 

		res.json(beacons);
	}); 
});

/* ADD a new beacon */
router.post('/', function(req, res, next) {
	var beacon = new Beacon({
		name: req.body.name,
		loc: req.body.loc,
		full: req.body.full
	});

	console.log(beacon);

	// save and return beacon
	beacon.save(function(err, beacon) {
		if (err) { 
			return next(err); 
		}

		res.status(201).json(beacon);
	});
});

module.exports = router;