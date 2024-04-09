import {
  ChatLog as ChatLogType,
  SystemMessage as SystemMessageType,
  UserMessage as UserMessageType,
} from '@/types';
import { Skeleton } from './ui/skeleton';
import { Zap } from 'lucide-react';
import { cn } from '@/lib/utils';
import { useFilters } from '@/store/use-filters';
import { defaultPlayer, reportedPlayer, reportingPlayer } from '@/assets';

type ChatLogProps = {
  chatLog: ChatLogType;
};

export const ChatLog = ({ chatLog }: ChatLogProps) => {
  const { systemMessages } = useFilters();
  return (
    <div className='flex flex-col gap-y-4'>
      {chatLog.messages.map((message, index) => {
        return message.issuer === 'SYSTEM' ? (
          systemMessages && <SystemMessage key={index} message={message} />
        ) : (
          <UserMessage key={index} message={message} />
        );
      })}
    </div>
  );
};

export const ChatLogSkeleton = () => {
  return (
    <div className='flex flex-col gap-y-4'>
      <Skeleton className='h-20 w-full' />
      <Skeleton className='h-20 w-full' />
      <Skeleton className='h-20 w-full' />
      <Skeleton className='h-20 w-full' />
    </div>
  );
};

type SystemMessageProps = {
  message: SystemMessageType;
};

const SystemMessage = ({ message }: SystemMessageProps) => {
  return (
    <div className='bg-slate-100 dark:bg-slate-800 rounded-lg border-2 border-b-4 border-slate-200 dark:border-slate-700 px-4 py-4 flex justify-between items-center gap-x-6 transition-colors'>
      <Zap
        fill='rgb(77 124 15 / var(--tw-text-opacity))'
        className='w-6 sm:w-8 h-6 sm:h-8 mx-2 text-lime-700'
      />
      <p className='flex-1 text-sm sm:text-lg'>{message.message}</p>
      <span className='text-xs sm:text-sm text-lime-700'>
        {message.time.toLocaleTimeString()}
      </span>
    </div>
  );
};

type UserMessageProps = {
  message: UserMessageType;
};

const UserMessage = ({ message }: UserMessageProps) => {
  const { highlightReported, highlightReporter } = useFilters();
  return (
    <div
      className={cn(
        'bg-slate-100 dark:bg-slate-800 rounded-lg px-4 py-2 flex justify-between items-center gap-x-6 border-2 border-b-4 border-slate-200 dark:border-slate-700 transition-colors',
        message.reported &&
          highlightReported &&
          'bg-rose-50 border-rose-500 dark:border-rose-500',
        message.reporter &&
          highlightReporter &&
          'bg-sky-50 border-sky-500 dark:border-sky-500'
      )}
    >
      <div className='flex flex-col justify-center items-center'>
        <img
          src={
            message.uniqueId
              ? `http://cravatar.eu/avatar/${message.uniqueId}`
              : message.reported
              ? reportedPlayer
              : message.reporter
              ? reportingPlayer
              : defaultPlayer
          }
          width={32}
          height={32}
          className='w-8 sm:w-12 h-8 sm:h-12 object-cover rounded-sm'
        />
        <span
          className={cn(
            'text-xs sm:text-sm font-semibold text-lime-700',
            message.reported && 'text-rose-500',
            message.reporter && 'text-sky-600'
          )}
        >
          {message.name}
        </span>
      </div>

      <p className='flex-1 text-sm sm:text-lg'>{message.message}</p>

      <span className='text-xs sm:text-sm text-lime-700'>
        {message.time.toLocaleTimeString()}
      </span>
    </div>
  );
};
