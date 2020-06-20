const express = require('express');
const cors = require('cors');

const app = express();

// ==> Rotas da API:
const index = require('./routes/index');
const TournamentsRoute = require('./routes/tournaments.routes');
const AuthenticationRoute = require('./routes/authentication.routes');
const TeamsRoute = require('./routes/teams.routes');
const PlayersRoute = require('./routes/players.routes');
const GamesRoute = require('./routes/games.routes');
const EventRoute = require('./routes/event.routes');
const GamesV2Route = require('./routes/games_v2.routes');
const GameEventsV2Route = require('./routes/game_events.routes');

app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use(express.json({ type: 'application/vnd.api+json' }));
app.use(cors());

app.use(index);
app.use('/api/', TournamentsRoute);
app.use('/user/', AuthenticationRoute);
app.use('/api/', TeamsRoute);
app.use('/api/', PlayersRoute);
app.use('/api/', GamesRoute);
app.use('/api/', EventRoute);
app.use('/api/', GamesV2Route);
app.use('/api/', GameEventsV2Route);

module.exports = app;