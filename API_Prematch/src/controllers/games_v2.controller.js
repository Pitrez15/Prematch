const sql = require('mssql');
const sqlConfig = require('../config/database');


// Database Connection
const pool1 = new sql.ConnectionPool(sqlConfig);
const pool1Connect = pool1.connect();

pool1.on('error', err => {

    console.log(err);
})


// Method to list all Games:

exports.listAllGames = async (req, res) => {

  await pool1Connect;

    try {

        const request = pool1.request();
        
        var scriptSQL = `select GAMES_V2.GAME_ID, GAMES_V2.HOME_TEAM_ID, A.TEAM_NAME as HOME_TEAM_NAME, GAMES_V2.AWAY_TEAM_ID, B.TEAM_NAME AWAY_TEAM_NAME, 
                                GAMES_V2.TOURNAMENT_ID, GAMES_V2.STAGE
                        from GAMES_V2
                        inner join TEAMS A on GAMES_V2.HOME_TEAM_ID = A.TEAM_ID
                        inner join TEAMS B on GAMES_V2.AWAY_TEAM_ID = B.TEAM_ID`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).json(result.recordset);
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


//Method to create new GAME:

exports.createNewGame = async (req, res) => {

    const { GAME_ID, HOME_TEAM_ID, AWAY_TEAM_ID, TOURNAMENT_ID, STAGE } = req.body;

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `insert into GAMES_V2 (GAME_ID, HOME_TEAM_ID, AWAY_TEAM_ID, TOURNAMENT_ID, STAGE) values 
                                    (`+GAME_ID+`,`+HOME_TEAM_ID+`,`+AWAY_TEAM_ID+`,`+TOURNAMENT_ID+`,'`+STAGE+`')`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).send({mensagem: "Game Created"});
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


// Method to list GAMES by GAME_ID:

exports.listGameByGameID = async (req, res) => {

    const GAME_ID = parseInt(req.params.GAME_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select GAMES_V2.GAME_ID, GAMES_V2.HOME_TEAM_ID, A.TEAM_NAME as HOME_TEAM_NAME, GAMES_V2.AWAY_TEAM_ID, 
                            B.TEAM_NAME AWAY_TEAM_NAME, GAMES_V2.TOURNAMENT_ID, GAMES_V2.STAGE
                            from GAMES_V2
                            inner join TEAMS A on GAMES_V2.HOME_TEAM_ID = A.TEAM_ID
                            inner join TEAMS B on GAMES_V2.AWAY_TEAM_ID = B.TEAM_ID
                            where GAMES_V2.GAME_ID=` +GAME_ID;

          const result = await request.query(scriptSQL);
          console.log(scriptSQL);
          console.dir(result.recordset);
          res.status(201).json(result.recordset);
      } 
      catch (err) {
  
          console.error('SQL error', err);
          res.status(500).send({mensagem: "Error connecting to DB"})
      }
  };



// Method to list All GAMES by TOURNAMENT_ID:

exports.listGameByTournamentID = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL =  `select GAMES_V2.GAME_ID, GAMES_V2.HOME_TEAM_ID, A.TEAM_NAME as HOME_TEAM_NAME, GAMES_V2.AWAY_TEAM_ID, 
                            B.TEAM_NAME AWAY_TEAM_NAME, GAMES_V2.TOURNAMENT_ID, GAMES_V2.STAGE
                            from GAMES_V2
                            inner join TEAMS A on GAMES_V2.HOME_TEAM_ID = A.TEAM_ID
                            inner join TEAMS B on GAMES_V2.AWAY_TEAM_ID = B.TEAM_ID
                            where GAMES_V2.TOURNAMENT_ID=` +TOURNAMENT_ID;

          const result = await request.query(scriptSQL);
          console.log(scriptSQL);
          console.dir(result.recordset);
          res.status(201).json(result.recordset);
      } 
      catch (err) {
  
          console.error('SQL error', err);
          res.status(500).send({mensagem: "Error connecting to DB"})
      }
  };


// Method to list All GAMES by TEAM_ID:

exports.listGameByTeamID = async (req, res) => {

    const TEAM_ID = parseInt(req.params.TEAM_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select GAMES_V2.GAME_ID, GAMES_V2.HOME_TEAM_ID, A.TEAM_NAME HOME_TEAM_NAME, GAMES_V2.AWAY_TEAM_ID, B.TEAM_NAME AWAY_TEAM_NAME, 
                                    GAMES_V2.TOURNAMENT_ID, GAMES_V2.STAGE
                            from GAMES_V2
                            inner join TEAMS A
                            on GAMES_V2.HOME_TEAM_ID = A.TEAM_ID
                            inner join TEAMS B
                            on GAMES_V2.AWAY_TEAM_ID = B.TEAM_ID 
                            where HOME_TEAM_ID =` +TEAM_ID+ ` OR AWAY_TEAM_ID =` +TEAM_ID;

          const result = await request.query(scriptSQL);
          console.log(scriptSQL);
          console.dir(result.recordset);
          res.status(201).json(result.recordset);
      } 
      catch (err) {
  
          console.error('SQL error', err);
          res.status(500).send({mensagem: "Error connecting to DB"})
      }
  };


  // Method to update a GAME:

exports.updateGame = async (req, res) => {

    const GAME_ID = parseInt(req.params.GAME_ID);

    const { HOME_TEAM_ID,AWAY_TEAM_ID,TOURNAMENT_ID,GOALS_HOME_TEAM,GOALS_AWAY_TEAM,STAGE } = req.body;

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update GAMES_V2 set HOME_TEAM_ID=`+HOME_TEAM_ID+`,AWAY_TEAM_ID=`+AWAY_TEAM_ID+`,
                        TOURNAMENT_ID=`+TOURNAMENT_ID+`, STAGE='`+STAGE+`'
                        where GAME_ID=` +GAME_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Game Updated"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};


// Method for deleting Games:

exports.deleteGame = async (req, res) => {

    const GAME_ID = parseInt(req.params.GAME_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from GAMES_V2 where GAME_ID=` +GAME_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Game deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};

// Method for deleting Games By Tournament:

exports.deleteGamesByTournamentID = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from GAMES_V2 where TOURNAMENT_ID=` +TOURNAMENT_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Games deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};