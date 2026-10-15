export default (app) => {
  app.route({ url: '/arrow-app', method: 'GET', handler: listItems });
};
