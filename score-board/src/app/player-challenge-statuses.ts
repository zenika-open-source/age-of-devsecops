export interface PlayerChallengeStatuses {
  playerId: string;
  score: number;
  challengeStatuses: Array<ChallengeStatus>;
  flagStatuses: FlagStatuses
}

interface ChallengeStatus {
  challenge: {
    name: string
  };
  completed: boolean;
}

interface FlagStatuses {
  publicPresent: number;
  publicTotal: number;
  secretPresent: number;
}
