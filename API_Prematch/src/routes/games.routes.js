const router = require('express-promise-router')();
const gamesController = require('../controllers/games.controller');

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


// CRUD - 'games':

// List all games: (GET): localhost:3000/api/games
router.get('/games', gamesController.listAllGames);

// Create New Game: (POST): localhost:3000/api/new_game
router.post('/new_game', gamesController.createNewGame);

// List Game by GAME_ID: (GET): localhost:3000/api/games/:GAME_ID
router.get('/games/:GAME_ID', gamesController.listGameByGameID);

// List Games by Tournament: (GET): localhost:3000/api/games/tournament/:TOURNAMENT_ID
router.get('/games/tournament/:TOURNAMENT_ID', gamesController.listGameByTournamentID);

// List Games by Team: (GET): localhost:3000/api/games/team/:TEAM_ID
router.get('/games/team/:TEAM_ID', gamesController.listGameByTeamID);

// Update Game by 'GAME_ID': (PUT): localhost: 3000/api/update_game/:GAME_ID
router.put('/update_game/:GAME_ID', gamesController.updateGame);

// Delete Game by 'GAME_ID': (PUT): localhost: 3000/api/delete_game/:GAME_ID
router.delete('/delete_game/:GAME_ID', gamesController.deleteGame);

// Delete Game by 'TOURNAMENT_ID': (PUT): localhost: 3000/api/delete_game/tournament/:TOURNAMENT_ID
router.delete('/delete_game/tournament/:TOURNAMENT_ID', gamesController.deleteGamesByTournamentID);

module.exports = router;