import { loadChatLog } from '@/actions';
import {
  ChatLog as ChatLogComponent,
  ChatLogSkeleton,
} from '@/components/ChatLog';
import { Filters } from '@/components/Filters';
import { FiltersReset } from '@/components/FiltersReset';
import { MoreInfo } from '@/components/MoreInfo';
import { Separator } from '@/components/ui/separator';
import { Skeleton } from '@/components/ui/skeleton';
import { ChatLog } from '@/types';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'sonner';

const ChatLogRoute = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [chatLog, setChatLog] = useState<ChatLog>();
  const [name, setName] = useState<string>();
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    setIsLoading(true);

    const load = async () => {
      const chatLog = await loadChatLog(id!, name);

      if (!chatLog) {
        navigate('/');
      }

      setChatLog(chatLog);
      setIsLoading(false);

      if (name && chatLog?.anonymized) {
        toast('Der Name des gemeldeten Spielers war nicht richtig.');
      }
    };

    load();
  }, [id, name]);

  return (
    <div className='max-w-screen-md w-full mx-auto py-8 px-4 space-y-8'>
      <div className='flex justify-between items-center'>
        <h1 className='text-4xl font-bold text-slate-800 dark:text-slate-200'>
          ChatLog #<span className='text-green'>{id}</span>
        </h1>
        {isLoading ? (
          <Skeleton className='h-12 w-36' />
        ) : (
          <span className='text-3xl font-bold text-slate-800 dark:dark-slate-200'>
            {chatLog?.createdAt.toLocaleDateString()}
          </span>
        )}
      </div>

      <Separator />

      {isLoading ? (
        <>
          <div className='flex flex-wrap gap-x-4 gap-y-2'>
            <Skeleton className='h-10 w-28' />
            <Skeleton className='h-10 w-52' />
            <Skeleton className='h-10 w-52' />
          </div>
          <ChatLogSkeleton />
        </>
      ) : (
        <>
          <div className='flex flex-wrap gap-x-4 gap-y-2'>
            <Filters />
            <FiltersReset />
            {chatLog?.anonymized && <MoreInfo setName={setName} />}
          </div>
          <ChatLogComponent chatLog={chatLog!} />
        </>
      )}
    </div>
  );
};

export default ChatLogRoute;
