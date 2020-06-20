const router = require('express-promise-router')();
const eventController = require('../controllers/event.controller');

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


// CRUD - 'Events':


router.get('/events', eventController.listAllEvents);


router.post('/new_event', eventController.createNewEvent);


router.get('/events/:EVENT_ID', eventController.listEventByID);


router.put('/update_event/:EVENT_ID', eventController.updateEvent);


router.delete('/delete_Event/:EVENT_ID', eventController.deleteEvent);


module.exports = router;