const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/polynomes',
    createProxyMiddleware({
      target: 'http://localhost:8082',
      changeOrigin: true,
    })
  );

  app.use(
    '/racines',
    createProxyMiddleware({
      target: 'http://localhost:8083',
      changeOrigin: true,
    })
  );

  app.use(
    '/api/factorisation',
    createProxyMiddleware({
      target: 'http://localhost:8084',
      changeOrigin: true,
    })
  );
};
