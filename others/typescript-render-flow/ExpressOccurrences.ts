import express from 'express';
const firstOccurrenceRouter = express.Router();
const secondOccurrenceRouter = express.Router();
function firstOccurrenceHandler() {}
function secondOccurrenceHandler() {}
firstOccurrenceRouter.get('/occurrences', firstOccurrenceHandler);
secondOccurrenceRouter.get('/occurrences', secondOccurrenceHandler);
