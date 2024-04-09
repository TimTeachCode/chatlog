import { useRef } from 'react';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from './ui/dialog';
import { Button } from './ui/button';
import { Info } from 'lucide-react';
import { Label } from './ui/label';
import { Input } from './ui/input';

type MoreInfoProps = {
  setName: React.Dispatch<React.SetStateAction<string | undefined>>;
};

export const MoreInfo = ({ setName }: MoreInfoProps) => {
  const inputRef = useRef<HTMLInputElement>(null);

  const onClick = () => {
    setName(inputRef.current!.value);
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant='primary'>
          <Info className='h-4 w-4 mr-2' />
          Mehr Informationen
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className='text-xl font-bold'>
            Mehr Informationen
          </DialogTitle>
          <DialogDescription className='text-base'>
            Gib den Namen vom gemeldeten Spieler an, um weitere Informationen
            zum ChatLog zu erhalten.
          </DialogDescription>
        </DialogHeader>

        <div className='flex flex-col mt-6 gap-y-3'>
          <Label className='uppercase font-bold text-sm'>
            Name des gemeldeten Spielers
          </Label>
          <Input ref={inputRef} placeholder='Herobrine' />
          <Button variant='primary' onClick={onClick}>
            Freischalten
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
