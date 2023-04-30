package communication.protocol_v2v1;

/**
 * Different Types of Messages, same provided protocol
 * @author Simon Naab, Pierre-Louis Suckrow
 */
public enum MessageType {
  // basic
  Sample,
  // communication_setup
  HelloClient,
  Alive,
  HelloServer,
  Welcome,
  // lobby
  PlayerValues,
  PlayerAdded,
  SetStatus,
  PlayerStatus,
  SelectMap,
  MapSelected,
  GameStarted,
  // chat
  SendChat,
  ReceivedChat,
  // special
  Error,
  ConnectionUpdate,
  // cards
  PlayCard,
  CardPlayed,

  // map
  // -game_move
  CurrentPlayer,
  ActivePhase,
  // --setup_phase
  SetStartingPoint,
  StartingPointTaken,
  // --uprade_phase
  RefillShop,
  ExchangeShop,
  BuyUpgrade,
  UpgradeBought,
  // --programm_phase
  YourCards,
  NotYourCards,
  ShuffleCoding,
  SelectedCard,
  CardSelected,
  SelectionFinished,
  TimerStarted,
  TimerEnded,
  CardsYouGotNow,
  // --activation_phase
  CurrentCards,
  ReplaceCard,

  // actions_events_effects
  Movement,
  PlayerTurning,
  DrawDamage,
  PickDamage,
  SelectedDamage,
  Animation,
  Reboot,
  RebootDirection,
  Energy,
  CheckPointReached,
  GameFinished,
  CheckpointMoved,
  ChooseRegister,
  RegisterChosen,
  ReturnCards,

  LaserAnimation;
}
