const router = require('express-promise-router')();
const authenticationController = require('../controllers/authentication.controller');	

// Rotas de autenticacao
router.post('/registry', authenticationController.registry);

router.post('/login', authenticationController.login);

router.get('/username/:USERNAME', authenticationController.listUserByUsername)

router.put('/recover/:USERNAME', authenticationController.recoverPassword)

router.put('/update_user/:USERNAME', authenticationController.updateUser)

module.exports = router;
