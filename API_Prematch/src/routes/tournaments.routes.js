const router = require('express-promise-router')();
const tournamentsController = require('../controllers/tournaments.controller');

const jwt = require('jsonwebtoken');
require('dotenv').config();

function verifyJWT(req, res, next){

    var token = req.headers['x-access-token'];
    if (!token) return res.status(401).send({ auth: false, message: 'No token provided.' });
    
    jwt.verify(token, process.env.API_SECRET, function(err, decoded) {
      if (err) return res.status(500).send({ auth: false, message: 'Failed to authenticate token.' });
      
      // se tudo estiver ok, salva no request para uso posterior
      req.userId = decoded.id;
      next();
    });
};


// CRUD - 'Tournaments':

// List all Tournaments: (GET): localhost:3000/api/tournaments
router.get('/tournaments', tournamentsController.listAllTournaments);

// Create New Tournaments: (POST): localhost:3000/api/new_tournament
router.post('/new_tournament', tournamentsController.createNewTournament);

// List Tournaments by ID: (GET): localhost:3000/api/tournaments/:TOURNAMENT_ID
router.get('/tournaments/:TOURNAMENT_ID', tournamentsController.listTournamentByID);

// Update Tournament by 'TOURNAMENT_ID': (PUT): localhost: 3000/api/update_tournament/:TOURNAMENT_ID
router.put('/update_tournament/:TOURNAMENT_ID', tournamentsController.updateTournament);

// Delete Tournament by 'TOURNAMENT_ID': (PUT): localhost: 3000/api/delete_tournament/:TOURNAMENT_ID
router.delete('/delete_tournament/:TOURNAMENT_ID', tournamentsController.deleteTournament);

module.exports = router;