const router = require('express-promise-router')();
const playersController = require('../controllers/players.controller');

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


// CRUD - 'players':

// List all players: (GET): localhost:3000/api/teams
router.get('/players', playersController.listAllPlayers);

// Create New players: (POST): localhost:3000/api/new_player
router.post('/new_player', playersController.createNewPlayer);

// List Player by PLAYER_ID: (GET): localhost:3000/api/players/:PLAYER_ID
router.get('/players/:PLAYER_ID', playersController.listPlayerByPlayerID);

// List Player by TEAM_ID: (GET): localhost:3000/api/players/teams/:TEAM_ID
router.get('/players/teams/:TEAM_ID', playersController.listPlayerByTeamID);

// Update Player by 'PLAYE_ID': (PUT): localhost: 3000/api/update_player/:PLAYER_ID
router.put('/update_player/:PLAYER_ID', playersController.updatePlayer);

// Delete Player by 'PLAYER_ID': (PUT): localhost: 3000/api/delete_player/:PLAYER_ID
router.delete('/delete_player/:PLAYER_ID', playersController.deletePlayer);

// Delete Player by 'TOURNAMENT_ID': (PUT): localhost: 3000/api/delete_player/tournament/:TOURNAMENT_ID
router.delete('/delete_player/tournament/:TOURNAMENT_ID', playersController.deletePlayersByTournamentID);

module.exports = router;