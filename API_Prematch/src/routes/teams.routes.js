const router = require('express-promise-router')();
const teamsController = require('../controllers/teams.controller');

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


// CRUD - 'teams':

// List all teams: (GET): localhost:3000/api/teams
router.get('/teams', teamsController.listAllTeams);

// Create New Teams: (POST): localhost:3000/api/new_team
router.post('/new_team', teamsController.createNewTeam);

// List Team by TEAM_ID: (GET): localhost:3000/api/Teams/:TEAM_ID
router.get('/teams/:TEAM_ID', teamsController.listTeamByTeamID);

// List Team by TOURNAMENT_ID: (GET): localhost:3000/api/teams/tournament/:TOURNAMENT_ID
router.get('/teams/tournament/:TOURNAMENT_ID', teamsController.listTeamByTournamentID);

// Update Team by 'Team_ID': (PUT): localhost: 3000/api/update_team/:TEAM_ID
router.put('/update_team/:TEAM_ID', teamsController.updateTeam);

// Delete Team by 'Team_ID': (PUT): localhost: 3000/api/delete_team/:TEAM_ID
router.delete('/delete_team/:TEAM_ID', teamsController.deleteTeam);

// Delete Team by 'TOURNAMENT_ID': (PUT): localhost: 3000/api/delete_team/tournament/:TOURNAMENT_ID
router.delete('/delete_team/tournament/:TOURNAMENT_ID', teamsController.deleteTeamsByTournament);

module.exports = router;