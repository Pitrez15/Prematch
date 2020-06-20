const sql = require('mssql');
const sqlConfig = require('../config/database');


// Database Connection
const pool1 = new sql.ConnectionPool(sqlConfig);
const pool1Connect = pool1.connect();

pool1.on('error', err => {

    console.log(err);
})


// Method to list all Players:

exports.listAllPlayers = async (req, res) => {

  await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `select P.PLAYER_ID, P.PLAYER_FIRST_NAME,P.PLAYER_LAST_NAME,P.POSITION,P.TEAM_ID,P.PLAYER_HEIGHT,P.PLAYER_AGE,P.TOURNAMENT_ID,T.TEAM_NAME
                        from PLAYERS P
                        inner join TEAMS T
                        on P.TEAM_ID = T.TEAM_ID`;

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


//Method to create new PLAYER:

exports.createNewPlayer = async (req, res) => {

    const { PLAYER_ID,PLAYER_FIRST_NAME,PLAYER_LAST_NAME,POSITION,TEAM_ID,PLAYER_HEIGHT,PLAYER_AGE,TOURNAMENT_ID } = req.body;

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `insert into PLAYERS (PLAYER_ID,PLAYER_FIRST_NAME,PLAYER_LAST_NAME,POSITION,TEAM_ID,PLAYER_HEIGHT,
                                            PLAYER_AGE,TOURNAMENT_ID) values 
                                            (`+PLAYER_ID+`,'`+PLAYER_FIRST_NAME+`','`+PLAYER_LAST_NAME+`','`+POSITION+`',
                                            `+TEAM_ID+`,`+PLAYER_HEIGHT+`,`+PLAYER_AGE+`,`+TOURNAMENT_ID+`)`;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        console.dir(result.recordset);
        res.status(201).send({mensagem: "PLAYER Created"});
    } 
    catch (err) {

        console.error('SQL error', err);
        res.status(500).send({mensagem: "Error connecting to DB"})
    }
};


// Method to list PLAYER by PLAYER_ID:

exports.listPlayerByPlayerID = async (req, res) => {

    const PLAYER_ID = parseInt(req.params.PLAYER_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select P.PLAYER_ID, P.PLAYER_FIRST_NAME,P.PLAYER_LAST_NAME,P.POSITION,P.TEAM_ID,P.PLAYER_HEIGHT,P.PLAYER_AGE,P.TOURNAMENT_ID,T.TEAM_NAME
                            from PLAYERS P
                            inner join TEAMS T
                            on P.TEAM_ID = T.TEAM_ID
                            where PLAYER_ID =` +PLAYER_ID;

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



// Method to list All PLAYERS by TEAM_ID:

exports.listPlayerByTeamID = async (req, res) => {

    const TEAM_ID = parseInt(req.params.TEAM_ID)

    await pool1Connect;
  
      try {
  
          const request = pool1.request();
          var scriptSQL = `select P.PLAYER_ID, P.PLAYER_FIRST_NAME,P.PLAYER_LAST_NAME,P.POSITION,P.TEAM_ID,P.PLAYER_HEIGHT,
                                    P.PLAYER_AGE,P.TOURNAMENT_ID,T.TEAM_NAME
                            from PLAYERS P
                            inner join TEAMS T
                            on P.TEAM_ID = T.TEAM_ID
                            where P.TEAM_ID =` +TEAM_ID;

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


// Method to update a PLAYER:

exports.updatePlayer = async (req, res) => {

    const PLAYER_ID = parseInt(req.params.PLAYER_ID);

    const { PLAYER_FIRST_NAME,PLAYER_LAST_NAME,POSITION,PLAYER_HEIGHT,PLAYER_AGE,TOURNAMENT_ID } = req.body;

    await pool1Connect;

    try {

        const request = pool1.request();
        var scriptSQL = `update PLAYERS set PLAYER_FIRST_NAME='`+PLAYER_FIRST_NAME+`',PLAYER_LAST_NAME='`+PLAYER_LAST_NAME+`',
                            POSITION='`+POSITION+`',PLAYER_HEIGHT='`+PLAYER_HEIGHT+`',PLAYER_AGE='`+PLAYER_AGE+`',TOURNAMENT_ID='`+TOURNAMENT_ID`'
                            where PLAYER_ID=` +PLAYER_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Player Updated"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Database error"})
    }
};


// Method for deleting PLAYER:

exports.deletePlayer = async (req, res) => {

    const PLAYER_ID = parseInt(req.params.PLAYER_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from PLAYERS where PLAYER_ID=` +PLAYER_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Player deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};


// Method for deleting PLAYER BY TOURNAMENT_ID:

exports.deletePlayersByTournamentID = async (req, res) => {

    const TOURNAMENT_ID = parseInt(req.params.TOURNAMENT_ID);

    await pool1Connect; 

    try {

        const request = pool1.request();
        var scriptSQL = `delete from PLAYERS where TOURNAMENT_ID=` +TOURNAMENT_ID;

        const result = await request.query(scriptSQL);
        console.log(scriptSQL);
        res.status(201).send({mensagem: "Player deleted"});
    } catch (err) {
        console.error('SQL error', err);
        res.status(500).send({mensagem: "Connection error"})
    }
};