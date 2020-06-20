const router = require('express-promise-router')();
const gameEventController = require('../controllers/game_events.controller');

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


// CRUD - 'Game_events':


router.get('/game_events', gameEventController.listAllGameEvents);


router.post('/new_game_event', gameEventController.createNewGameEvent);


router.get('/game_events/game/:GAME_ID', gameEventController.listGameEventByGameID);


router.get('/game_events/:GAME_EVENT_ID', gameEventController.listGameEventByGameEventID);


router.put('/update_game_event/:GAME_ID/:GAME_EVENT_ID', gameEventController.updateGameEvent);


router.delete('/delete_game_event/:GAME_EVENT_ID', gameEventController.deleteGameEvent);


router.delete('/delete_game_event/game/:GAME_ID', gameEventController.deleteGameEventsByGameID)


module.exports = router;